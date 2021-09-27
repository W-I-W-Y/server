package wiwy.covid.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wiwy.covid.domain.Comment;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public Long delete(Long commentId) {
        Comment comment = findById(commentId);
        em.remove(comment);
        return commentId;
    }

}
