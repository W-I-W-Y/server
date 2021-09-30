package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.*;
import wiwy.covid.repository.*;
import wiwy.covid.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final MemberService memberService;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    // 특정 포스트 보기
    @GetMapping("/api/post/view/{postId}")
    public PostAndCommentDTO viewPost(@PathVariable Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        PostAndCommentDTO pac = new PostAndCommentDTO();
        if (findPost.isPresent()) {
            PostOutputDTO post = new PostOutputDTO(findPost.get());
            List<Comment> comments = commentRepository.findByPost(findPost.get());
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            pac.setPostOutputDTO(post);
            pac.setCommentOutputDTOS(collect);
            return pac;
        } else {
            throw new IllegalStateException("viewPost : 존재하지 않는 게시글입니다.");
        }
    }

    // 한 유저가 작성한 포스트 전체 보기
    @GetMapping("/api/post/viewByMember/{pageNum}")
    public List<PostOutputDTO> viewPostByMember(Authentication authentication, @PathVariable Integer pageNum) {
        Member findMember = memberService.getMemberFromToken(authentication);
        if (pageNum == null) {
            Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByMember(findMember, pageable);
            List<PostOutputDTO> collect = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return collect;
        } else {
            Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByMember(findMember, pageable);
            List<PostOutputDTO> collect = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return collect;
        }
    }

    // 해당 게시판에 포스트 작성
    @PostMapping("/api/post/add/{boardId}")
    public String addPost(@PathVariable Long boardId, @RequestBody PostInputDTO postDTO, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Board> findBoard = boardRepository.findById(boardId);
        postRepository.save(new Post(postDTO, member, findBoard.get()));
        return "addPost";
    }

    // 작성한 포스트 수정
    @PatchMapping("/api/post/modify/{postId}")
    public String modifyPost(@PathVariable Long postId, Authentication authentication, @RequestBody PostInputDTO postInputDTO) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("modifyPost : 존재하지 않는 게시글입니다.");
        }
        Post modifiedPost = findPost.get().modify(postInputDTO);
        postRepository.save(modifiedPost);
        return "modifyPost";
    }

    // 포스트 삭제
    @PostMapping("/api/post/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            postRepository.deleteById(postId);
            return "deletePost";
        } else {
            throw new IllegalStateException("deletePost : 존재하지 않는 게시글입니다.");
        }
    }

    // 게시글 좋아요
    @PatchMapping("/api/post/like/{postId}")
    public String likePost(@PathVariable Long postId, Authentication authentication) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("likePost : 존재하지 않는 게시글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);

        // 이미 이 사람이 좋아요를 눌렀었는지 확인
        if (member.isLikePost(postId)) { // 이미 좋아요를 누름 -> 좋아요 취소
            member.removeLikePost(postId);
            findPost.get().minusLike();
            postRepository.save(findPost.get());
            return "cancelLike";
        } else { // 안눌렀다면 -> 좋아요
            member.addLikePost(postId);
            findPost.get().plusLike();
            postRepository.save(findPost.get());
            return "submitLike";
        }

    }

    // 게시글 싫어요
    @PatchMapping("/api/post/like/{postId}")
    public String hatePost(@PathVariable Long postId, Authentication authentication) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("likePost : 존재하지 않는 게시글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);

        // 이미 이 사람이 싫어요를 눌렀었는지 확인
        if (member.isHatePost(postId)) { // 이미 싫어요를 누름 -> 싫어요 취소
            member.removeHatePost(postId);
            findPost.get().minusHate();
            postRepository.save(findPost.get());
            return "cancelHate";
        } else { // 안눌렀다면 -> 싫어요
            member.addHatePost(postId);
            findPost.get().plusHate();
            postRepository.save(findPost.get());
            return "submitHate";
        }

    }
}
