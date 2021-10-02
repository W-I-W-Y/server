package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.DTO.board.BoardDTO;
import wiwy.covid.domain.Post;
import wiwy.covid.domain.DTO.post.PostOutputDTO;
import wiwy.covid.repository.BoardRepository;
import wiwy.covid.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    // 게시판 추가하기
    @PostMapping("/api/board/add")
    public String addBoard(@RequestBody BoardDTO boardDTO) {
        Optional<Board> findBoard = boardRepository.findByBoardName(boardDTO.getBoardName());
        if (findBoard.isPresent()) {
            throw new IllegalStateException("이름이 중복인 게시판입니다.");
        } else {
            boardRepository.save(new Board(boardDTO));
            return "addBoard";
        }
    }

    // 게시판 목록 보기
    @GetMapping("/api/board/view")
    public List<Board> viewBoard() {
        return boardRepository.findAll();
    }

    // 게시판 삭제하기
    @DeleteMapping("/api/board/delete")
    public String deleteBoard(@RequestBody BoardDTO boardDTO) {
        Optional<Board> findBoard = boardRepository.findByBoardName(boardDTO.getBoardName());
        log.info("findBoard = {}", findBoard.get().getBoardName());
        if (findBoard.isPresent()) {
            List<Post> posts = postRepository.findByBoard(findBoard.get());
            List<Long> ids = new ArrayList<>();
            for (Post post : posts) {
                ids.add(post.getId());
            }
            postRepository.deleteAllByIdInQuery(ids);
            boardRepository.delete(findBoard.get());
            return "deleteBoard";
        }
        else {
            throw new IllegalStateException("해당 게시판이 존재하지 않습니다.");
        }
    }

    // 해당 게시판의 게시글 목록 보기
    @GetMapping("/api/board/{boardId}/view/{pageNum}")
    public List<PostOutputDTO> viewPostOnBoard(@PathVariable Long boardId, @PathVariable Integer pageNum) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new IllegalStateException("viewPostOnBoard : 존재하지 않는 게시판입니다.");
        }
        if (pageNum == null) {
            Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByBoard(findBoard.get(), pageable);
            List<PostOutputDTO> postDTOs = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return postDTOs;

        } else {
            Pageable pageable = PageRequest.of(pageNum, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByBoard(findBoard.get(), pageable);
            List<PostOutputDTO> postDTOs = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return postDTOs;
        }
    }
}
