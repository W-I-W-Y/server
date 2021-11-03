package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.*;
import wiwy.covid.domain.DTO.comment.CommentInputDTO;
import wiwy.covid.domain.DTO.comment.CommentOutputDTO;
import wiwy.covid.paging.CustomPagination;
import wiwy.covid.repository.CommentRepository;
import wiwy.covid.repository.HateCommentRepository;
import wiwy.covid.repository.LikeCommentRepository;
import wiwy.covid.repository.PostRepository;
import wiwy.covid.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final LikeCommentRepository likeCommentRepository;
    private final HateCommentRepository hateCommentRepository;

    // 댓글 작성
    @PostMapping("/api/comment/add/{postId}")
    public String addComment(@RequestBody CommentInputDTO commentInputDTO, Authentication authentication, @PathVariable Long postId) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("addComment : 존재하지 않는 게시글입니다.");
        }
        Comment comment = new Comment(commentInputDTO, member, findPost.get());
        findPost.get().addComments(comment);
        commentRepository.save(comment);
        return "addComment";
    }

    // 자신이 작성한 댓글 보기 페이지네이션 정보 주기
    @GetMapping("/api/comment/viewByMember/pagination")
    public CustomPagination viewCommentByMemberPagination(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        Page<Comment> comments = commentRepository.findByMember(member, pageable);
        return new CustomPagination(comments.getTotalElements(), comments.getTotalPages());
    }

    // 자신이 작성한 댓글 보기
    @GetMapping("/api/comment/viewByMember/{pageNum}")
    public List<CommentOutputDTO> viewCommentByMember(Authentication authentication, @PathVariable Integer pageNum) {
        Member member = memberService.getMemberFromToken(authentication);
        if (pageNum == 0) {
            Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
            Page<Comment> comments = commentRepository.findByMember(member, pageable);
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            return collect;
        } else {
            Pageable pageable = PageRequest.of(pageNum-1, 15, Sort.by("createTime").descending());
            Page<Comment> comments = commentRepository.findByMember(member, pageable);
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            return collect;
        }
    }

    // 댓글 삭제
    @PostMapping("/api/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isPresent() && member.getId().equals(findComment.get().getMember().getId())) {
            commentRepository.delete(findComment.get());
            return "deleteComment";
        } else {
            throw new IllegalStateException("deleteComment : 존재하지 않는 댓글입니다.");
        }
    }

    // 댓글 좋아요
    @PatchMapping("/api/comment/like/{commentId}")
    public String likeComment(@PathVariable Long commentId, Authentication authentication) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new IllegalStateException("likeComment : 존재하지 않는 댓글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);
        Comment comment = findComment.get();

        // 1. 이 게시글에 좋아요가 있는지 확인
        List<LikeComment> findLC = likeCommentRepository.findByCommentId(comment.getId());
        if (findLC == null || findLC.isEmpty()) { // 좋아요가 없는 게시글 일 때
            LikeComment likeComment = new LikeComment(member.getId(), comment.getId());
            likeCommentRepository.save(likeComment);
        } else { // 좋아요가 있는 게시글 일 때
            for (LikeComment lc : findLC) {
                if (lc.getMemberId().equals(member.getId())) { // 이미 좋아요를 누른 게시글 일 때 -> 좋아요 취소

                    likeCommentRepository.delete(lc);
                    comment.minusLike();
                    commentRepository.save(comment);
                    return "cancelLike";
                }
            }
            // 좋아요 누름
            LikeComment likeComment = new LikeComment(member.getId(), comment.getId());
            likeCommentRepository.save(likeComment);
        }
        comment.plusLike();
        commentRepository.save(comment);
        return "submitLike";
    }

    // 댓글 싫어요
    @PatchMapping("/api/comment/hate/{commentId}")
    public String hateComment(@PathVariable Long commentId, Authentication authentication) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new IllegalStateException("likeComment : 존재하지 않는 댓글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);
        Comment comment = findComment.get();

        // 1. 이 게시글에 싫어요가 있는지 확인
        List<HateComment> findHC = hateCommentRepository.findByCommentId(comment.getId());
        if (findHC == null || findHC.isEmpty()) { // 좋아요가 없는 게시글 일 때
            HateComment hateComment = new HateComment(member.getId(), comment.getId());
            hateCommentRepository.save(hateComment);
        } else { // 싫어요가 있는 게시글 일 때
            for (HateComment hc : findHC) {
                if (hc.getMemberId().equals(member.getId())) { // 이미 싫어요를 누른 게시글 일 때 -> 싫어요 취소

                    hateCommentRepository.delete(hc);
                    comment.minusHate();
                    commentRepository.save(comment);
                    return "cancelHate";
                }
            }
            // 싫어요 누름
            HateComment hateComment = new HateComment(member.getId(), comment.getId());
            hateCommentRepository.save(hateComment);
        }
        comment.plusHate();
        commentRepository.save(comment);
        return "submitHate";
    }

}
