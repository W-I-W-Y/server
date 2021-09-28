package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;
import wiwy.covid.repository.MemberRepositoryImpl;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);

        member.setRoles("ROLE_USER");
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        memberRepository.save(member);

        return member.getId();
    }


    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByUsername(member.getUsername());
        if(findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findByUsername(String username) {
        Optional<Member> byUsername = memberRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            Member member = byUsername.get();
            return member;
        } else {
            return null;
        }
    }

    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("찾는 회원이 존재하지 않습니다.");
        }
        return findMember.get();
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
