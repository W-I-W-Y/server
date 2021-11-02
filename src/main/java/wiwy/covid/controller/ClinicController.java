package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.apicall.repository.ClinicRepository;
import wiwy.covid.apicall.clinicdto.Clinic;
import wiwy.covid.apicall.clinicdto.ClinicDTO;
import wiwy.covid.domain.Member;
import wiwy.covid.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ClinicController {

    private final ClinicRepository clinicRepository;
    private final MemberService memberService;

    @GetMapping("/api/clinic/view")
    public List<ClinicDTO> viewClinic(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        if (member == null) {
            return null;
        }
        List<Clinic> clinics = clinicRepository.findBySidoNm(member.getRegion());
        List<ClinicDTO> collect = clinics.stream().map(ClinicDTO::new).collect(Collectors.toList());
        return collect;
    }
}
