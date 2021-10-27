package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardName(String boardName);
    List<Board> findByBoardNameContaining(String boardName);
}
