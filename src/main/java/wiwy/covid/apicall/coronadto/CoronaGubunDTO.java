package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoronaGubunDTO {

    private String gubun;
    private int defCnt;
    private int incDec;

    public CoronaGubunDTO(CoronaData coronaData) {
        this.gubun = coronaData.getGubun();
        this.defCnt = coronaData.getDefCnt();
        this.incDec = coronaData.getIncDec();
    }
}
