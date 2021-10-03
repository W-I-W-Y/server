package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.LikePost;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    List<LikePost> findByPostId(Long postId);
    List<LikePost> findByMemberId(Long memberId);



    @Query(value = "select * from like_post l where l.post_id = :postId and l.member_id = :memberId",nativeQuery = true)
    Optional<LikePost> findByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
