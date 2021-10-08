package wiwy.covid.apicall.vaccinedto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Vaccine {

    @Id @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    private String tpcd;
    private Integer firstCnt;
    private Integer secondCnt;

    @CreationTimestamp
    private Timestamp createDt;
}
