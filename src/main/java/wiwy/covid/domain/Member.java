package wiwy.covid.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

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

    private String role; // ROLE_USER, ROLE_ADMIN

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;


    // 빌더를 만들었기 때문에 기본 생성자가 있어야 함. 접근제한을 위해 protected 사용
    protected Member() {

    }

    @Builder
    public Member(String username, String password, String email, String role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
