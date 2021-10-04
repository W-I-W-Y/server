package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.apicall.coronadto.CoronaData;


import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Repository
@Transactional(readOnly = true)
public class CoronaRepository {

    private final EntityManager em;

    @Transactional
    public void save(CoronaData coronaDto) {
        em.persist(coronaDto);
        log.info("CoronaDto saved, gubun = {}", coronaDto.getGubun());
    }

    public List<CoronaData> findRecentCorona() {
        return em.createQuery("select c from CoronaDto c order by c.seq desc", CoronaData.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
    }

    public List<CoronaData> findCoronaPerDay() {
        return em.createQuery("select c from CoronaDto c order by c.seq desc", CoronaData.class)
                .setFirstResult(0)
                .setMaxResults(18)
                .getResultList();
    }

    public List<CoronaData> findHapGae() {
        return em.createQuery("select c from CoronaDto c order by c.seq desc", CoronaData.class)
                .setFirstResult(18)
                .setMaxResults(1)
                .getResultList();
    }

    public List<CoronaData> findConfirmedPerWeek() {
        return em.createQuery("select c from CoronaDto c where c.gubun = '합계' order by c.seq desc", CoronaData.class)
                .setFirstResult(0)
                .setMaxResults(7)
                .getResultList();
    }


}
