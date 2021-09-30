package wiwy.covid.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class PostDTO {
    private String postName;
    private String content;
}
