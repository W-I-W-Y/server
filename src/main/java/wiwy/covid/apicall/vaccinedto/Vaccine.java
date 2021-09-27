package wiwy.covid.apicall.vaccinedto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Vaccine {

    @Id @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    private String tpcd;
    private Integer firstCnt;
    private Integer secondCnt;
}
