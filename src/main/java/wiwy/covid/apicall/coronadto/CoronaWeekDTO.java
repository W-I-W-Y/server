package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoronaWeekDTO {

    private int defCnt;
    private String stdDay;

    public CoronaWeekDTO(CoronaData coronaData) {
        this.defCnt = coronaData.getDefCnt();
        this.stdDay = coronaData.getStdDay();
    }
}
