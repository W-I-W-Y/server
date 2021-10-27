package wiwy.covid.domain.DTO.member;

import lombok.Getter;

@Getter
public class MemberInputDTO {

    private String username;
    private String password;
    private String email;
    private String region;
}
