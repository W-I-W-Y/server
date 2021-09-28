package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;
    private final PostService postService;

    // 특정 게시판에 존재하는 특정 게시글 보기
    @GetMapping("/{boardId}/view/{postId}")
    public String viewPost(@PathVariable Long boardId, @PathVariable Long postId, Model model) {
        Board board = boardService.findOne(boardId);
        Post post = postService.findOne(postId);

        model.addAttribute("board", board);
        model.addAttribute("post", post);
        return "post/post_main";
    }

    // 특정 게시판에 게시글 생성
    @GetMapping("/{boardId}/add")
    public String getAddPost() {
        return "post/addPostForm";
    }

    @PostMapping("/api/post/add")
    public Long addPost(@RequestBody Post post, Long boardId) {
        Board board = boardService.findOne(boardId);
        Long postId = postService.post(board, post);
        return postId;

    }

    // 특정 게시판에 게시글 생성
    @PostMapping("/{boardId}/add")
    public String postAddPost(@PathVariable Long boardId, Post post, RedirectAttributes redirectAttributes) {
        Board board = boardService.findOne(boardId);
        Long postId = postService.post(board, post);
        redirectAttributes.addAttribute("boardId", boardId);
        redirectAttributes.addAttribute("postId",postId);
        return "redirect:/{boardId}/view/{postId}";
    }

}
