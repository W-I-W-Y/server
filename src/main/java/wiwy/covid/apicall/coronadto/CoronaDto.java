package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter @Setter
@ToString
@Entity
public class CoronaDto {

    @Id @GeneratedValue
    @Column(name = "corona_id")
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

}
