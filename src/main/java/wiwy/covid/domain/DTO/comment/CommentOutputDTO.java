package wiwy.covid.domain.DTO.comment;

import lombok.Getter;
import wiwy.covid.domain.Comment;

import java.sql.Timestamp;

@Getter
public class CommentOutputDTO {

    private Long id;
    private String content;
    private String calculateTime;
    private Timestamp createTime;
    private String username;
    private String postName;
    private int likes;
    private int hates;

    public CommentOutputDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.calculateTime = comment.calculateTime(comment.getCreateTime());
        this.createTime = comment.getCreateTime();
        this.username = comment.getMember().getUsername();
        this.postName = comment.getPost().getPostName();
        this.likes = comment.getLikes();
        this.hates = comment.getHates();
    }
}
