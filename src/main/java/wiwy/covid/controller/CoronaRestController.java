package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.apicall.AbrCoronaRepository;
import wiwy.covid.apicall.CoronaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.coronadto.CoronaData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CoronaRestController {

    private final CoronaRepository coronaRepository;
    private final AbrCoronaRepository abrCoronaRepository;

    @GetMapping("/api/covid")
    public String showCovid(Model model) {

        // 코로나 확진자 현황
        List<CoronaData> hapGae = coronaRepository.findHapGae();
        CoronaData coronaToday = hapGae.get(0);
        model.addAttribute("coronaToday", coronaToday);

        // 날짜별 코로나 확진자 현황
//        List<String> coronaDays = new ArrayList<>();
        List<CoronaData> confirmedPerWeek = coronaRepository.findConfirmedPerWeek();
        List<Integer> confirmedCnts = new ArrayList<>();
        List<String> confirmedDays = new ArrayList<>();
        for (CoronaData weekDTO : confirmedPerWeek) {
            confirmedCnts.add(weekDTO.getIncDec());
            confirmedDays.add(weekDTO.getStdDay());
        }
        confirmedDays.sort(Comparator.naturalOrder());
        Collections.reverse(confirmedCnts);
        model.addAttribute("confirmedCnts",confirmedCnts);
        model.addAttribute("confirmedDays", confirmedDays);

        // 시도별 코로나 확진자 현황
        List<CoronaData> cities = coronaRepository.findCoronaPerDay();
        List<Integer> cityCnts = new ArrayList<>();
        for (CoronaData city : cities) {
            cityCnts.add(city.getDefCnt());
        }
        cityCnts.sort(Comparator.reverseOrder());
        model.addAttribute("cityCnts",cityCnts);



        // 국외 확진자 현황
        List<AbrCoronaDto> recent = abrCoronaRepository.findRecent();


        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 d일");
        String curDate = date.format(df);
        curDate = curDate + " 00시";

        List<AbrCoronaDto> abroads = new ArrayList<>();

        // 국외 코로나 업데이트 된 경우
        if(curDate == recent.get(0).getStdDay()) {
            abroads = abrCoronaRepository.findByDate(curDate);
        } else { // 새벽이어서 업데이트 안 된 경우
            date = date.minusDays(1);
            curDate = date.format(df) + " 00시";
            abroads = abrCoronaRepository.findByDate(curDate);
        }



        model.addAttribute("abroads", abroads);
        return "main";
    }
}
