package wiwy.covid.apicall.dismsgdto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DisasterMsg {
    private DisMsgHead head;
    private DisMsg row;
}
