package wiwy.covid.domain.DTO.member;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import wiwy.covid.domain.Member;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class MemberDTO {

    private Long id;

    private String username;
    private String password;
    private String email;

    private String role; // ROLE_USER, ROLE_ADMIN
    private String region;

    private Timestamp createDate;

    public MemberDTO(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = null;
        this.email = member.getEmail();
        this.region = member.getRegion();
        List<String> memberRoles = member.getRoleList();
        if (memberRoles.contains("ROLE_ADMIN")) {
            this.role = "admin";
        } else if (memberRoles.contains("ROLE_MANAGER")) {
            this.role = "manager";
        } else if (memberRoles.contains("ROLE_USER")) {
            this.role = "user";
        }
        this.createDate = member.getCreateDate();
    }
}
