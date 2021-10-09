package wiwy.covid.apicall.vaccinedto;

import lombok.Getter;


@Getter
public class VaccineChartDTO {

    private Integer incFirstCnt;
    private Integer incSecondCnt;

    public VaccineChartDTO(Vaccine vaccineChartDTO) {
        this.incFirstCnt = vaccineChartDTO.getFirstCnt();
        this.incSecondCnt = vaccineChartDTO.getSecondCnt();
    }
}
