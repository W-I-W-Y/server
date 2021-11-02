package wiwy.covid.apicall.clinicdto;

import lombok.Getter;

@Getter
public class ClinicDTO {

    private String adtFrDd;
    private String hospTyTpCd;
    private String sgguNm;
    private String sidoNm;
    private String spclAdmTyCd;
    private String telno;
    private String yadmNm;

    public ClinicDTO(Clinic clinic) {
        this.adtFrDd = clinic.getAdtFrDd();
        this.hospTyTpCd = clinic.getHospTyTpCd();
        this.sgguNm = clinic.getSgguNm();
        this.sidoNm = clinic.getSidoNm();
        this.spclAdmTyCd = clinic.getSpclAdmTyCd();
        this.telno = clinic.getTelno();
        this.yadmNm = clinic.getYadmNm();
    }
}
