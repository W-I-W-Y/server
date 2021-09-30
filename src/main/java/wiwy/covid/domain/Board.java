package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
