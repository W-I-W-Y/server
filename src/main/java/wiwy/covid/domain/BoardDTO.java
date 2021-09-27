package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardDTO {

    private Board board;
    private List<PostDTO> postDTOS = new ArrayList<>();
}
