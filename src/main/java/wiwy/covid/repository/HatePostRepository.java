package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.HatePost;

import java.util.List;

public interface HatePostRepository extends JpaRepository<HatePost, Long> {

    List<HatePost> findByPostId(Long postId);
}
