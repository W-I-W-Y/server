package wiwy.covid.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDTOByMember {

    private Long id;
    private String postName;
    private String content;
    private String calculateTime;
    private Timestamp createTime;
    private int viewCnt;
    private int likes;
    private String username;
    private String boardName;
    private int comCounts; // 댓글 수

    public PostDTOByMember(Post post) {
        this.id = post.getId();
        this.postName = post.getPostName();
        this.content = post.getContent();
        this.calculateTime = post.calculateTime(post.getCreateTime());
        this.createTime = post.getCreateTime();
        this.viewCnt = post.getViewCnt();
        this.likes = post.getLikes();
        this.username = post.getMember().getUsername();
        this.boardName = post.getBoard().getBoardName();
        this.comCounts = post.getComments().size();
    }
}
