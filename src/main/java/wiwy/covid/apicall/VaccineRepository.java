package wiwy.covid.apicall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.vaccinedto.Vaccine;



public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    Page<Vaccine> findFirstByTpcd(String tpcd, Pageable pageable);
    Vaccine findFirstByTpcd(String tpcd, Sort sort);
    Vaccine findFirstByOrderByIdDesc();

}
