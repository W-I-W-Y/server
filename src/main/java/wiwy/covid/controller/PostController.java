package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.*;
import wiwy.covid.repository.BoardRepository;
import wiwy.covid.repository.PostRepository;
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

    // 특정 포스트 보기
    @GetMapping("/api/post/view/{postId}")
    public PostDTOByMember viewPost(@PathVariable Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            return new PostDTOByMember(findPost.get());
        } else {
            throw new IllegalStateException("viewPost : 존재하지 않는 게시글입니다.");
        }
    }

    // 한 유저가 작성한 포스트 전체 보기
    @GetMapping("/api/post/viewByMember")
    public List<PostDTOByMember> viewPostByMember(Authentication authentication) {
        Member findMember = memberService.getMemberFromToken(authentication);
        List<Post> posts = postRepository.findByMember(findMember);
        List<PostDTOByMember> collect = posts.stream().map(post -> new PostDTOByMember(post)).collect(Collectors.toList());
        return collect;
    }

    // 해당 게시판에 포스트 작성
    @PostMapping("/api/post/add/{boardId}")
    public String addPost(@PathVariable Long boardId, @RequestBody PostDTO postDTO, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Board> findBoard = boardRepository.findById(boardId);
        postRepository.save(new Post(postDTO, member, findBoard.get()));
        return "addPost";
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
}
