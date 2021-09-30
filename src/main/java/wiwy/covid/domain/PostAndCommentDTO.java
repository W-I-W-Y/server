package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostAndCommentDTO {

    private PostOutputDTO postOutputDTO;
    private List<CommentOutputDTO> commentOutputDTOS = new ArrayList<>();
}
