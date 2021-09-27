package wiwy.covid.apicall.dismsgdto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class DisMsg {

    @Id @GeneratedValue
    @Column(name = "disMsg_id")
    private Long id;

    private String create_date;
    private int location_id;
    private String location_name;
    private int md101_sn;
    private String msg;
    private String send_platform;
}
