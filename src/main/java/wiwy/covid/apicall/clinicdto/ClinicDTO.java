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
        if (clinic.getSpclAdmTyCd().equals("A0")) {
            this.spclAdmTyCd = "국민안심병원";
        } else if (clinic.getSpclAdmTyCd().equals("97")) {
            this.spclAdmTyCd = "코로나검사 실시기관";
        } else if (clinic.getSpclAdmTyCd().equals("99")) {
            this.spclAdmTyCd = "코로나 선별진료소 운영기관";
        } else {
            this.spclAdmTyCd = null;
        }
        this.telno = clinic.getTelno();
        this.yadmNm = clinic.getYadmNm();
    }
}
