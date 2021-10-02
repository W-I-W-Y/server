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
import wiwy.covid.repository.CommentRepository;
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

    // 자신이 작성한 댓글 보기
    @GetMapping("/api/comment/viewByMember/{pageNum}")
    public List<CommentOutputDTO> viewCommentByMember(Authentication authentication, @PathVariable Integer pageNum) {
        Member member = memberService.getMemberFromToken(authentication);
        if (pageNum == null) {
            Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
            Page<Comment> comments = commentRepository.findByMember(member, pageable);
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            return collect;
        } else {
            Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
            Page<Comment> comments = commentRepository.findByMember(member, pageable);
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            return collect;
        }
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isPresent()) {
            commentRepository.delete(findComment.get());
            return "deleteComment";
        } else {
            throw new IllegalStateException("deleteComment : 존재하지 않는 댓글입니다.");
        }
    }



}
