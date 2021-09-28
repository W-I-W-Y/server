package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.*;
import wiwy.covid.paging.BoardPaging;
import wiwy.covid.paging.Paging;
import wiwy.covid.repository.PostRepository;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.MemberService;
import wiwy.covid.service.PostService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final PostService postService;
    private final BoardPaging boardPaging;
    private final PostRepository postRepository;

    @ResponseBody
    @GetMapping("/api/community/main")
    public BoardDTO communityMain() {
        List<Board> boards = boardService.findAllBoards();
        BoardDTO boardDTO = new BoardDTO();
        for (Board board : boards) {
            Pageable page = PageRequest.of(0, 5, Sort.by("createTime").descending());
            Page<Post> pagingPosts = postRepository.findByBoard(board, page);
            List<PostDTO> postDTOList = pagingPosts.stream().map(pagingPost -> new PostDTO(pagingPost, pagingPost.getMember().getUsername())).collect(Collectors.toList());
            PageImpl<PostDTO> postDTOS = new PageImpl<>(postDTOList, page, pagingPosts.getTotalElements());
            boardDTO.setPosts(postDTOS);
        }
        return boardDTO;
    }

//    @GetMapping("/co")
//    public String viewAllBoards(@RequestParam(required = false) Long memberId, Model model) {
//
//        if(memberId != null) {
//            Member member = memberService.findOne(memberId);
//            model.addAttribute("member", member);
//        }
//
//
//        List<Board> boards = boardService.findAllBoards();
//        List<BoardDTO> boardDTOS = new ArrayList<>();
//        for (Board board : boards) {
//            List<PostDTO> postDTOS = new ArrayList<>();
//            BoardDTO boardDTO = new BoardDTO();
//            Page<Post> posts = postService.pagingPosts(board.getId(), 0, 5);
//            for (Post post : posts) {
//                PostDTO postDTO = new PostDTO();
//                postDTO.setPost(post);
//                postDTO.setPostTime(post.calculateTime(post.getCreateTime()));
//                postDTOS.add(postDTO);
//            }
//            boardDTO.setBoard(board);
//            boardDTO.setPostDTOS(postDTOS);
//            boardDTOS.add(boardDTO);
//        }
//        model.addAttribute("boardDTOS", boardDTOS);
//
//        return "board/community";
//    }
//
//    // 특정 게시판 보기
//    @GetMapping("/{boardId}")
//    public String viewOneBoard(@PathVariable Long boardId, Model model) {
//        Board board = boardService.findOne(boardId);
//        Page<Post> posts = postService.pagingPosts(boardId, 0, 10);
//
//        List<PostDTO> postDTOS = new ArrayList<>();
//        for (Post post : posts) {
//            PostDTO postDTO = new PostDTO();
//            postDTO.setPost(post);
//            postDTO.setPostTime(post.calculateTime(post.getCreateTime()));
//            postDTOS.add(postDTO);
//        }
//        model.addAttribute("board",board);
//        model.addAttribute("postDTOS", postDTOS);
//
//
//        return "board/board_main";
//    }

//    // 게시판 페이징
//    @GetMapping("/{boardId}/page/{pageNum}")
//    public String viewPage(@PathVariable Long boardId, @PathVariable int pageNum, Model model) {
//        Board board = boardService.findOne(boardId);
//        List<Post> posts = postService.findPostsByBoardId(boardId);
//
//        Page<Post> returnPosts = postService.pagingPosts(boardId, pageNum, 10);
//
//        List<PostDTO> postDTOS = new ArrayList<>();
//        for (Post post : returnPosts) {
//            PostDTO postDTO = new PostDTO();
//            postDTO.setPost(post);
//            postDTO.setPostTime(post.calculateTime(post.getCreateTime()));
//            postDTOS.add(postDTO);
//        }
//
//        model.addAttribute("board", board);
//        model.addAttribute("postDTOS", postDTOS);
//
//
//        return "/board/main";
//    }
//
//    @GetMapping("/board/add")
//    public String getEditBoard() {
//        return "board/addBoardForm";
//    }
//
//    @PostMapping("/board/add")
//    public String postEditBoard(Board board, RedirectAttributes redirectAttributes) {
//        try {
//            Long boardId = boardService.makeBoard(board);
//            redirectAttributes.addAttribute("boardId", boardId);
//        } catch (IllegalStateException e) {
//            redirectAttributes.addAttribute("dup",true);
//            return "redirect:/addBoard";
//        }
//
//        return "redirect:/{boardId}";
//    }


}
