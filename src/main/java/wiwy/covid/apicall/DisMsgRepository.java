package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.apicall.dismsgdto.DisMsg;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class DisMsgRepository {

    private final EntityManager em;

    @Transactional
    public void save(DisMsg disMsg) {
        em.persist(disMsg);
        log.info("DisMsg updated, md101_sn = {}", disMsg.getMd101_sn());
    }

    public List<DisMsg> findBySN(int currentSN) {
        return em.createQuery("select d from DisMsg d where d.md101_sn = :currentSN", DisMsg.class)
                .setParameter("currentSN", currentSN)
                .getResultList();
    }

    public List<DisMsg> findAll() {
        return em.createQuery("select d from DisMsg d", DisMsg.class).getResultList();
    }

    public List<DisMsg> findRecentDisMsg() {
        return em.createQuery("select d from DisMsg d order by d.md101_sn desc", DisMsg.class)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
    }
}
