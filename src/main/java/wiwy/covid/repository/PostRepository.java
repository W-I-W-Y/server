package wiwy.covid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member member);
    List<Post> findByPostName(String postName);
    List<Post> findByBoardId(Long boardId);
    //페이징 만들기
    Page<Post> findByBoardId(Long boardId, Pageable pageable);

    Page<Post> findByBoard(Board board, Pageable pageable);
    Page<Post> findByMember(Member member, Pageable pageable);
}
