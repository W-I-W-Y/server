package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.domain.DTO.board.BoardDTO;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String boardName;
    private String description;

    protected Board() {
    }

    public Board(BoardDTO boardDTO) {
        this.boardName = boardDTO.getBoardName();
        this.description = boardDTO.getDescription();
    }
}
