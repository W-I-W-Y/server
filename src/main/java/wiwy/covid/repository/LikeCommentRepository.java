package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wiwy.covid.domain.LikeComment;

import java.util.List;
import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findByCommentId(Long commentId);

    List<LikeComment> findByMemberId(Long memberId);

    @Query(value = "select * from like_comment l where l.comment_id = :commentId and l.member_id = :memberId",nativeQuery = true)
    Optional<LikeComment> findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
}
