package wiwy.covid.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wiwy.covid.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String userName) {
        return em.createQuery("select m from Member m where m.userName = :name", Member.class)
                .setParameter("name", userName)
                .getResultList();

    }

    public Long delete(Long memberId) {
        Member member = findById(memberId);
        em.remove(member);
        return memberId;
    }
}
