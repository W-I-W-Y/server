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
import wiwy.covid.service.MemberService;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 조회 매핑
    @GetMapping("/member/info")
    public String member(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member currentMember = principalDetails.getMember();
        log.debug("currentMember = {}", currentMember);
        model.addAttribute("member", currentMember);

        return "member/memberInfo";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/signIn";
    }

    @GetMapping("/joinForm")
    public String joinForm() { return "member/signUp"; };

    @PostMapping("/addMember")
    public String addMember(Member member) {
        memberService.join(member);
        return "redirect:/loginForm";
    }




    @GetMapping("/denied")
    @ResponseBody
    public String deniedView() {
        return "denied";
    }

}
