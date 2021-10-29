package wiwy.covid.domain.DTO.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import wiwy.covid.domain.Board;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardDTO {

    private String boardName;
    private String description;

    public BoardDTO() {
    }

    public BoardDTO(Board board) {
        this.boardName = board.getBoardName();
        this.description = board.getDescription();
    }
}
