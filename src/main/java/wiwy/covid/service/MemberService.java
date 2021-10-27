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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.config.auth.PrincipalDetails;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;


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
        // 제주, 경남, 경북, 전남, 전북, 충남, 충북, 강원, 경기, 세종, 울산, 대전, 광주, 인천, 대구, 부산, 서울
        if (member.getRegion().equals("서울")) {
            member.addRole("ROLE_SEOUL");
        } else if (member.getRegion().equals("부산")) {
            member.addRole("ROLE_BUSAN");
        } else if (member.getRegion().equals("대구")) {
            member.addRole("ROLE_DAEGU");
        } else if (member.getRegion().equals("인천")) {
            member.addRole("ROLE_INCHEON");
        } else if (member.getRegion().equals("광주")) {
            member.addRole("ROLE_GWANGJU");
        } else if (member.getRegion().equals("대전")) {
            member.addRole("ROLE_DAEJEON");
        } else if (member.getRegion().equals("울산")) {
            member.addRole("ROLE_ULSAN");
        } else if (member.getRegion().equals("세종")) {
            member.addRole("ROLE_SEJONG");
        } else if (member.getRegion().equals("경기")) {
            member.addRole("ROLE_GYEONGGI");
        } else if (member.getRegion().equals("강원")) {
            member.addRole("ROLE_GANGWON");
        } else if (member.getRegion().equals("충북")) {
            member.addRole("ROLE_CHUNGBUK");
        } else if (member.getRegion().equals("충남")) {
            member.addRole("ROLE_CHUNGNAM");
        } else if (member.getRegion().equals("전북")) {
            member.addRole("ROLE_JEONBUK");
        } else if (member.getRegion().equals("전남")) {
            member.addRole("ROLE_JEONNAM");
        } else if (member.getRegion().equals("경북")) {
            member.addRole("ROLE_GYEONGBUK");
        } else if (member.getRegion().equals("경남")) {
            member.addRole("ROLE_GYEONGNAM");
        } else if (member.getRegion().equals("제주")) {
            member.addRole("ROLE_JEJU");
        } else {
            throw new IllegalStateException("MemberService.join : 올바르지 않은 지역입니다.");
        }

        memberRepository.save(member);

        return member.getId();
    }


    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByUsername(member.getUsername());
        if(findMember.isPresent()) {
            if (findMember.get().getId().equals(member.getId())) {
                // 회원정보를 수정하는 경우는 이름이 중복이기때문에 패스
                return;
            }
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member getMemberFromToken(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getMember();
    }
}
