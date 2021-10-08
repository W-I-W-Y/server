package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaOutputDTO;
import wiwy.covid.apicall.vaccinedto.VaccineDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CoronaOutputDTO {

    CoronaTodayDTO coronaTodayDTO; // 당일 확진확자, 격리해제, 격리중, 사망, 전날에 비해서 늘어난 확진환자 수
    List<CoronaWeekDTO> coronaWeekDTOS = new ArrayList<>(); // 7일간 확진환자 수
    List<CoronaGubunDTO> coronaGubunDTOS = new ArrayList<>(); // 시도별 확진자 ( 해당 시도, 확진자 수, 전날과 비교해서 늘어난 확진 환자 수)
    List<AbrCoronaOutputDTO> abrCoronaDtos = new ArrayList<>(); // 국외 발생동향
    VaccineDTO vaccineDTO; // 백신

}
