package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.repository.BoardRepository;
import wiwy.covid.repository.BoardRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long makeBoard(Board board) {
        validateDuplicateBoard(board);
        boardRepository.save(board);
        return board.getId();
    }

    // 중복 게시판 검증
    private void validateDuplicateBoard(Board board) {
        Optional<Board> findBoard = boardRepository.findByBoardName(board.getBoardName());
        if(findBoard.isPresent()) {
            throw new IllegalStateException("이미 존재하는 게시판입니다.");
        }
    }

    public Board findOne(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new IllegalStateException("찾으려는 게시판이 존재하지 않습니다.");
        }
        return findBoard.get();
    }

    public Optional<Board> findBoardByName(String boardName) {
        return boardRepository.findByBoardName(boardName);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public void deleteBoard(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            // 삭제할 보드가 없다
            throw new IllegalStateException("삭제할 게시판이 존재하지 않는 게시판입니다.");
        }
        Board deleteBoard = findBoard.get();
        boardRepository.delete(deleteBoard);
    }
}
