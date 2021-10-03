package wiwy.covid.domain.DTO.post;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.domain.DTO.comment.CommentOutputDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostAndCommentDTO {

    private Long boardId;
    private PostOutputDTO postOutputDTO;
    private List<CommentOutputDTO> commentOutputDTOS = new ArrayList<>();
    private boolean isAuthor = false;
    private boolean isLike = false;
    private boolean isHate = false;
}
