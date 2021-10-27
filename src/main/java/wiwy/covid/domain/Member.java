package wiwy.covid.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import wiwy.covid.domain.DTO.member.MemberInputDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private String region;

    private String roles; // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Timestamp createDate;

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void addRole(String role) {
        this.roles = this.roles + "," + role;
    }


    // 빌더를 만들었기 때문에 기본 생성자가 있어야 함. 접근제한을 위해 protected 사용
    protected Member() {

    }

    public Member(MemberInputDTO memberInputDTO) {
        this.username = memberInputDTO.getUsername();
        this.password = memberInputDTO.getPassword();
        this.email = memberInputDTO.getEmail();
        this.region = memberInputDTO.getRegion();
    }
}
