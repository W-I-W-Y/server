package wiwy.covid.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class PostDTO {

    private Long id;
    private String boardName;
    private String postName;
    private String content;
    private Timestamp createTime;
    private int viewCnt;
    private int likes;
    private String username;
    private int commentCount;
    private String postTime;

    public PostDTO() {
    }

    @Builder
    public PostDTO(Post post, String username) {
        this.id = post.getId();
        this.boardName = post.getBoard().getBoardName();
        this.postName = post.getPostName();
        this.content = post.getContent();
        this.createTime = post.getCreateTime();
        this.viewCnt = post.getViewCnt();
        this.likes = post.getLikes();
        this.username = username;
        this.commentCount = post.getComments().size();
        this.postTime = post.calculateTime(post.getCreateTime());
    }
}
