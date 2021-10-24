package wiwy.covid.apicall;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import java.util.List;
import java.util.Optional;

public interface AbrCoronaRepository extends JpaRepository<AbrCoronaDto, Long> {

    Optional<AbrCoronaDto> findFirstByOrderByIdDesc();

    List<AbrCoronaDto> findByStdDayContaining(String stdDay);
}
