package wiwy.covid.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostRepositoryImpl {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findByMemberId(Long memberId) {
        return em.createQuery("select p from Post p join p.member m where m.id = :memberId", Post.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    public List<Post> findByName(String postName) {
        return em.createQuery("select p from Post p where p.postName = :postName", Post.class)
                .setParameter("postName", postName)
                .getResultList();
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findPostsByBoardId(Long boardId) {
        return em.createQuery("select p from Post p join p.board b where b.id = :boardId", Post.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Long delete (Long postId) {
        Post post = findById(postId);
        em.remove(post);
        return postId;
    }

    // pageNum 수에 맞추어 페이징하여 게시글 find
    public List<Post> pagingPosts(Long boardId, int page, int perPageNum) {
        return em.createQuery("select p from Post p join p.board b where b.id = :boardId order by p.id desc", Post.class)
                .setParameter("boardId", boardId)
                .setFirstResult(page)
                .setMaxResults(perPageNum)
                .getResultList();
    }

}
