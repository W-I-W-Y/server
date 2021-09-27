package wiwy.covid.apicall.abroadcoronadto;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
public class AbrCoronaDto {

    @Id @GeneratedValue
    @Column(name = "abrcorona_id")
    private Long id;

    private String areaNm;
    private String areaNmCn;
    private String areaNmEn;
    private String createDt;
    private int natDeathCnt;
    private String natDeathRate;
    private int natDefCnt;
    private String nationNm;
    private String nationNmCn;
    private String nationNmEn;
    private int seq;
    private String stdDay;
    private String updateDt;
}
