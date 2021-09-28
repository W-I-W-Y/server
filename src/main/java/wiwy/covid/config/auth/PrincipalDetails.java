package wiwy.covid.config.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import wiwy.covid.domain.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails {

    private Member member;

    // 일반 로그인 생성자
    public PrincipalDetails(Member member) {
        this.member = member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(r-> {
            authorities.add(() -> r);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 만료되었는 지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호를 오랫동안 사용했는 지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리 사이트에서 회원이 1년동안 로그인을 하지 않았다면 휴면계정으로 하기로 함
        // 그러면 현재 시간 - 로그인 시간 => 1년을 초과하면 return false;
        return true;
    }
}
