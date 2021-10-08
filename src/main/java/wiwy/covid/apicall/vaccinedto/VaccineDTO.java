package wiwy.covid.apicall.vaccinedto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VaccineDTO {

    private Integer firstCnt;
    private Integer secondCnt;
    private Integer incFirstCnt;
    private Integer incSecondCnt;

    public VaccineDTO(Vaccine total, Vaccine inc) {
        this.firstCnt = total.getFirstCnt();
        this.secondCnt = total.getSecondCnt();
        this.incFirstCnt = inc.getFirstCnt();
        this.incSecondCnt = inc.getSecondCnt();
    }
}
