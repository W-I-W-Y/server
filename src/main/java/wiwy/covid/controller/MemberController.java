package wiwy.covid.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.config.auth.PrincipalDetails;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.MemberRepository;
import wiwy.covid.service.MemberService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public String join(@RequestBody Member member) {
        memberService.join(member);
        return "회원가입 완료";

    }

    @GetMapping("/api/member")
    public String member() {
        return "member";
    }

    @PostMapping("/api/login")
    public String login(@RequestBody String username, @RequestBody String password) {
        return "hi";
    }

}
