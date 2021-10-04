package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wiwy.covid.domain.HateComment;
import wiwy.covid.domain.LikeComment;

import java.util.List;
import java.util.Optional;

public interface HateCommentRepository extends JpaRepository<HateComment, Long> {
    List<HateComment> findByCommentId(Long commentId);

    List<HateComment> findByMemberId(Long memberId);

    @Query(value = "select * from hate_comment h where h.comment_id = :commentId and h.member_id = :memberId",nativeQuery = true)
    Optional<HateComment> findByCommentIdAndMemberId(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
}
