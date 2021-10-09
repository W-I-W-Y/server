package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoronaWeekDTO {

    private int incDec;
    private String stdDay;

    public CoronaWeekDTO(CoronaData coronaData) {
        this.incDec = coronaData.getIncDec();
        this.stdDay = coronaData.getStdDay();
    }
}
