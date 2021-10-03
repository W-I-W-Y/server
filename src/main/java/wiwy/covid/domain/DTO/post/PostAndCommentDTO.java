package wiwy.covid.domain.DTO.post;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.domain.DTO.comment.CommentOutputDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostAndCommentDTO {

    private PostOutputDTO postOutputDTO;
    private List<CommentOutputDTO> commentOutputDTOS = new ArrayList<>();
    private boolean isLike = false;
    private boolean isHate = false;
}
