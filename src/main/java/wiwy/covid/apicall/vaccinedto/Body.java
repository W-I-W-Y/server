package wiwy.covid.apicall.vaccinedto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Body {
    private String dataTime;
    private List<Vaccine> items = new ArrayList<>();
}
