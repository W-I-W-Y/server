package wiwy.covid.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.DTO.member.MemberDTO;
import wiwy.covid.domain.DTO.member.MemberInputDTO;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;
import wiwy.covid.service.MemberService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/api/join")
    public String join(@RequestBody Member member) {
        memberService.join(member);
        return "회원가입 완료";
    }

    // 회원의 정보 보기
    @GetMapping("/api/member/info")
    public MemberDTO memberInfo(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        MemberDTO memberDTO = new MemberDTO(member);
        return memberDTO;
    }

    // 회원 정보 수정
    @PatchMapping("/api/member/modify")
    public String modifyMember(Authentication authentication, MemberInputDTO memberInputDTO) {

        Member findMember = memberService.getMemberFromToken(authentication);

        // 비밀번호 수정 함
        if (memberInputDTO.getPassword() != null) {
            if (memberInputDTO.getPassword().equals(findMember.getPassword())) {
                findMember.setPassword(memberInputDTO.getPassword());

            } else {
                // 기존의 비밀번호를 틀림
                throw new IllegalStateException("기존 비밀번호를 틀렸습니다.");
            }
        }

        // 유저네임 수정
        if (!memberInputDTO.getUsername().equals(findMember.getUsername())) {
            findMember.setUsername(memberInputDTO.getUsername());
        }

        // 이메일 수정
        if (!memberInputDTO.getEmail().equals(findMember.getEmail())) {
            findMember.setEmail(memberInputDTO.getEmail());
        }

        memberService.join(findMember);
        return "modifyMember";
    }



}
