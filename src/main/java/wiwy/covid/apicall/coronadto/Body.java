package wiwy.covid.apicall.coronadto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Body {
    private List<CoronaDto> items = new ArrayList<>();
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
