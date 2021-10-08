package wiwy.covid.apicall.abroadcoronadto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbrCoronaOutputDTO {

    private String areaNm;
    private int natDeathCnt;
    private int natDefCnt;
    private String nationNm;

    public AbrCoronaOutputDTO(AbrCoronaDto abrCoronaDto) {
        this.areaNm = abrCoronaDto.getAreaNm();
        this.natDeathCnt = abrCoronaDto.getNatDeathCnt();
        this.natDefCnt = abrCoronaDto.getNatDefCnt();
        this.nationNm = abrCoronaDto.getNationNm();
    }
}
