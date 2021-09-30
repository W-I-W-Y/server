package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.LikePost;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    List<LikePost> findByPostId(Long postId);
}
