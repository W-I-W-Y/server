package wiwy.covid.apicall.dismsgdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DisMsgHead {
    private int totalCount;
    private int numOfRows;
    private int pageNo;
    private String type;
    private DisMsgResult RESULT;
}
