package wiwy.covid.apicall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.coronadto.CoronaData;
import wiwy.covid.apicall.vaccinedto.Vaccine;

import java.util.List;


public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    Page<Vaccine> findFirstByTpcd(String tpcd, Pageable pageable);
    Vaccine findFirstByTpcd(String tpcd, Sort sort);
    Vaccine findFirstByOrderByIdDesc();
    List<Vaccine> findTop7ByTpcd(String tpcd, Sort sort);

}
