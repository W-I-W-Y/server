package wiwy.covid.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;

import java.util.Optional;


@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        if (findMember.isEmpty()) {
            return null;
        } else {
            Member member = findMember.get();
            return new PrincipalDetails(member);
        }
    }
}
