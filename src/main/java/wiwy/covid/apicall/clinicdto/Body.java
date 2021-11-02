package wiwy.covid.apicall.clinicdto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Body {
    private List<Clinic> items = new ArrayList<>();
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
