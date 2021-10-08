package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoronaTodayDTO {

    private int defCnt;
    private int isolClearCnt;
    private int isolIngCnt;
    private int deathCnt;
    private int incDec;

    public CoronaTodayDTO(CoronaData coronaData) {
        this.defCnt = coronaData.getDefCnt();
        this.isolClearCnt = coronaData.getIsolClearCnt();
        this.isolIngCnt = coronaData.getIsolIngCnt();
        this.deathCnt = coronaData.getDeathCnt();
        this.incDec = coronaData.getIncDec();
    }
}
