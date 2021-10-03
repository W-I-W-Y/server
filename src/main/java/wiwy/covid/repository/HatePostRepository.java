package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wiwy.covid.domain.HatePost;
import wiwy.covid.domain.LikePost;

import java.util.List;
import java.util.Optional;

public interface HatePostRepository extends JpaRepository<HatePost, Long> {

    List<HatePost> findByPostId(Long postId);
    List<HatePost> findByMemberId(Long memberId);

    @Query(value = "select h from HatePost h where h.postId = :postId and h.memberId = :memberId")
    Optional<HatePost> findByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
