package wiwy.covid.apicall;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import java.util.List;

public interface AbrCoronaRepository extends JpaRepository<AbrCoronaDto, Long> {

    AbrCoronaDto findFirstByOrderByIdDesc();

    List<AbrCoronaDto> findByStdDayContaining(String stdDay);
}
