package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.apicall.ClinicRepository;
import wiwy.covid.apicall.clinicdto.Clinic;
import wiwy.covid.apicall.clinicdto.ClinicDTO;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ClinicController {

    private final ClinicRepository clinicRepository;

    @GetMapping("/api/clinic/view")
    public List<ClinicDTO> viewClinic(String sidoNm) {
        List<Clinic> clinics = clinicRepository.findBySidoNm(sidoNm);
        List<ClinicDTO> collect = clinics.stream().map(ClinicDTO::new).collect(Collectors.toList());
        return collect;
    }
}
