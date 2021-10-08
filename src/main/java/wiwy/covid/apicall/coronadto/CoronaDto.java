package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoronaDto {

    private Long id;

    private String createDt;
    private int deathCnt;
    private int defCnt;
    private String gubun;
    private String gubunCn;
    private String gubunEn;
    private int incDec;
    private int isolClearCnt;
    private int isolIngCnt;
    private int localOccCnt;
    private int overFlowCnt;
    private String qurRate;
    private int seq;
    private String stdDay;
    private String updateDt;

    public CoronaDto(CoronaData coronaData) {
        this.id = coronaData.getId();
        this.createDt = coronaData.getCreateDt();
        this.deathCnt = coronaData.getDeathCnt();
        this.defCnt = coronaData.getDefCnt();
        this.gubun = coronaData.getGubun();
        this.gubunCn = coronaData.getGubunCn();
        this.gubunEn = coronaData.getGubunEn();
        this.incDec = coronaData.getIncDec();
        this.isolClearCnt = coronaData.getIsolClearCnt();
        this.isolIngCnt = coronaData.getIsolIngCnt();
        this.localOccCnt = coronaData.getLocalOccCnt();
        this.overFlowCnt = coronaData.getOverFlowCnt();
        this.qurRate = coronaData.getQurRate();
        this.seq = coronaData.getSeq();
        this.stdDay = coronaData.getStdDay();
        this.updateDt = coronaData.getUpdateDt();
    }
}
