package wiwy.covid.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wiwy.covid.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public List<Board> findByName(String boardName) {
        return em.createQuery("select b from Board b where b.boardName = :boardName",Board.class)
                .setParameter("boardName",boardName)
                .getResultList();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public Long delete(Long boardId) {
        Board board = findById(boardId);
        em.remove(board);
        return boardId;
    }
}
