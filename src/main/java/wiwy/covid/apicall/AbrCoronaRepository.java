package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Repository
public class AbrCoronaRepository {

    private final EntityManager em;

    @Transactional
    public void save(AbrCoronaDto abrCoronaDto) {
        em.persist(abrCoronaDto);
        log.info("AbrCoronaDto savd, nationNm = {}", abrCoronaDto.getNationNm());

    }

    public List<AbrCoronaDto> findRecent() {
        return em.createQuery("select a from AbrCoronaDto a order by a.seq desc", AbrCoronaDto.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
    }

    public List<AbrCoronaDto> findAbrCoronaPerDay() {
        return em.createQuery("select a from AbrCoronaDto a order by a.seq desc", AbrCoronaDto.class)
                .setFirstResult(0)
                .setMaxResults(190)
                .getResultList();
    }

    public List<AbrCoronaDto> findByDate(String curDate) {
        return em.createQuery("select a from AbrCoronaDto a where a.stdDay = :curDate order by a.natDefCnt desc")
                .setParameter("curDate", curDate)
                .getResultList();
    }


}
