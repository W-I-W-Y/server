package wiwy.covid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);
    List<Post> findByMember(Member member);
    List<Post> findByPostName(String postName);
    List<Post> findByPostNameContaining(String postName);
    Page<Post> findByPostNameContaining(String postName, Pageable pageable);
    List<Post> findByBoardId(Long boardId);
    //페이징 만들기
    Page<Post> findByBoardId(Long boardId, Pageable pageable);

    Page<Post> findByBoard(Board board, Pageable pageable);
    Page<Post> findByMember(Member member, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from Post p where p.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
