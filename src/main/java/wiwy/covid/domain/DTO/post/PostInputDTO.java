package wiwy.covid.domain.DTO.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class PostInputDTO {
    private String postName;
    private String content;
}
