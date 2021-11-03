package wiwy.covid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.HatePost;
import wiwy.covid.domain.LikePost;

import java.util.List;
import java.util.Optional;

public interface HatePostRepository extends JpaRepository<HatePost, Long> {

    List<HatePost> findByPostId(Long postId);
    List<HatePost> findByMemberId(Long memberId);

    @Transactional
    @Modifying
    @Query(value = "delete from HatePost h where h.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Query(value = "select * from hate_post h where h.post_id = :postId and h.member_id = :memberId",nativeQuery = true)
    Optional<HatePost> findByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") Long memberId);

    Page<HatePost> findByMemberId(Long memberId, Pageable pageable);
}
