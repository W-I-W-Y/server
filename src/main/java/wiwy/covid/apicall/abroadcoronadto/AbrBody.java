package wiwy.covid.apicall.abroadcoronadto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AbrBody {

    private List<AbrCoronaDto> items = new ArrayList<>();
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
