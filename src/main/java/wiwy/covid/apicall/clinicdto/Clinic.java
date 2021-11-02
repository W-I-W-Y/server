package wiwy.covid.apicall.clinicdto;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Clinic {

    @Id @GeneratedValue
    private Long id;

    private String adtFrDd;
    private String hospTyTpCd;
    private String sgguNm;
    private String sidoNm;
    private String spclAdmTyCd;
    private String telno;
    private String yadmNm;

}
