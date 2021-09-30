package wiwy.covid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wiwy.covid.domain.Comment;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByMember(Member member, Pageable pageable);

    List<Comment> findByPost(Post post);

}
