package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaOutputDTO;
import wiwy.covid.apicall.comparator.AbrCoronaComparator;
import wiwy.covid.apicall.comparator.CoronaDataComparator;
import wiwy.covid.apicall.coronadto.*;
import wiwy.covid.apicall.repository.AbrCoronaRepository;
import wiwy.covid.apicall.repository.CoronaRepository;
import wiwy.covid.apicall.repository.VaccineRepository;
import wiwy.covid.apicall.vaccinedto.Vaccine;
import wiwy.covid.apicall.vaccinedto.VaccineChartDTO;
import wiwy.covid.apicall.vaccinedto.VaccineDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CoronaController {

    private final CoronaRepository coronaRepository;
    private final AbrCoronaRepository abrCoronaRepository;
    private final VaccineRepository vaccineRepository;

    @GetMapping("/api/corona")
    public CoronaOutputDTO show() {

        CoronaOutputDTO coronaOutputDTO = new CoronaOutputDTO();
        // 날짜를 가져옴 -> 그 날짜의 데이터가 업데이트 되어 있는지 확인해야 함
        LocalDate t = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String compareToday = t.format(df);
        String today = t.format(df) + " 00시";
        LocalDate y = t.minusDays(1);
        String compareYest = y.format(df);
        String yesterday = y.format(df) + " 00시";

        CoronaData todayCorona = coronaRepository.findByStdDayAndGubun(today, "합계");
        log.info("todayCorona = {}", todayCorona);
        /**
         *  7일간 확진환자 수
         */
        List<CoronaData> coronaWeeks = coronaRepository.findTop7ByGubun("합계", Sort.by("createDt").descending());
        coronaWeeks.sort(new CoronaDataComparator());
        List<CoronaWeekDTO> coronaWeekDTOS = coronaWeeks.stream().map(coronaWeek -> new CoronaWeekDTO(coronaWeek)).collect(Collectors.toList());
        coronaOutputDTO.setCoronaWeekDTOS(coronaWeekDTOS);
        if (todayCorona == null) { // 그 날짜의 데이터가 없으면 -> 새벽이어서 그 날 데이터가 없음 -> 어제 데이터를 보여줘야함
            /**
             *  시도별 확진자 수
             */
            List<CoronaData> coronas = coronaRepository.findByStdDay(yesterday);
            List<CoronaGubunDTO> collect = coronas.stream().map(corona -> new CoronaGubunDTO(corona)).collect(Collectors.toList());
            coronaOutputDTO.setCoronaGubunDTOS(collect);
            /**
             *  당일 확진확자, 격리해제, 격리중, 사망, 전날에 비해서 늘어난 확진환자 수
             */
            CoronaData yestCorona = coronaRepository.findByStdDayAndGubun(yesterday, "합계");
            if (yestCorona == null) {
                throw new IllegalStateException("CoronaController : yesterday corona 가 존재하지 않습니다.");
            }
            CoronaTodayDTO coronaTodayDTO = new CoronaTodayDTO(yestCorona);
            coronaOutputDTO.setCoronaTodayDTO(coronaTodayDTO);




        } else { // 그 날짜의 데이터가 있으면 -> 업데이트가 되어있다는 거
            /**
             *  시도별 확진자 수
             */
            List<CoronaData> coronas = coronaRepository.findByStdDay(today);
            List<CoronaGubunDTO> collect = coronas.stream().map(corona -> new CoronaGubunDTO(corona)).collect(Collectors.toList());
            coronaOutputDTO.setCoronaGubunDTOS(collect);
            /**
             *  당일 확진확자, 격리해제, 격리중, 사망, 전날에 비해서 늘어난 확진환자 수
             */
            CoronaTodayDTO coronaTodayDTO = new CoronaTodayDTO(todayCorona);
            coronaOutputDTO.setCoronaTodayDTO(coronaTodayDTO);

        }

        /**
         * 국외 발생동향
         */

        Optional<AbrCoronaDto> recent = abrCoronaRepository.findFirstByOrderByIdDesc();
        if (recent.isEmpty()) {
            throw new IllegalStateException("국외 데이터가 아예 없음");
        } else {
            String stdDay = recent.get().getStdDay();
            String cp = stdDay.substring(0, stdDay.length()-4);
            List<AbrCoronaDto> abrCoronaDtoList = abrCoronaRepository.findByStdDayContaining(cp);
            abrCoronaDtoList.sort(new AbrCoronaComparator().reversed());
            List<AbrCoronaOutputDTO> abrCoronaOutputDTOList = abrCoronaDtoList.stream().map(abrCoronaDto -> new AbrCoronaOutputDTO(abrCoronaDto)).collect(Collectors.toList());
            coronaOutputDTO.setAbrCoronaDtos(abrCoronaOutputDTOList);
        }


        /**
         * 백신 접종 현황
         */
//        Pageable pageable = PageRequest.of(0,2, Sort.by("firstCnt").descending());
        Vaccine total = vaccineRepository.findFirstByTpcd("전체건수(C): (A)+(B)", Sort.by("firstCnt").descending());
        Vaccine inc = vaccineRepository.findFirstByTpcd("당일실적(A)", Sort.by("firstCnt").descending());
        List<Vaccine> vaccineList = vaccineRepository.findTop7ByTpcd("당일실적(A)", Sort.by("firstCnt").descending());
        List<VaccineChartDTO> collect = vaccineList.stream().map(vaccine -> new VaccineChartDTO(vaccine)).collect(Collectors.toList());
        VaccineDTO vaccineDTO = new VaccineDTO(total, inc);
        coronaOutputDTO.setVaccineDTO(vaccineDTO);
        coronaOutputDTO.setVaccineChartDTOS(collect);


        return coronaOutputDTO;
    }

}
