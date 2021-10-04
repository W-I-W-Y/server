package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;
import wiwy.covid.service.MemberService;

@RequiredArgsConstructor
@RestController
public class ManagerController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/api/manager/info")
    public String managerInfo(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);

        String hi = member.getRoles();
        return hi;

    }
}
