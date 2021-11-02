package wiwy.covid.apicall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.apicall.clinicdto.Clinic;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findBySidoNm(String sidoNm);
}
