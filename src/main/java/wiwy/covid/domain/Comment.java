package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import wiwy.covid.domain.DTO.comment.CommentInputDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private int likes;
    private int hates;

    @CreationTimestamp
    private Timestamp createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Comment() {
    }

    public Comment(CommentInputDTO commentInputDTO, Member member, Post post) {
        this.content = commentInputDTO.getContent();
        this.likes = 0;
        this.hates = 0;
        this.member = member;
        this.post = post;
    }

    public void plusLike() {
        this.likes += 1;
    }
    public void minusLike() {
        this.likes -= 1;
    }
    public void plusHate() {
        this.hates += 1;
    }
    public void minusHate() {
        this.hates -= 1;
    }

    private static class TIME_MAXIMUM {

        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public String calculateTime(Timestamp paramDate) {

//        Date date = Date.from(paramDate.atZone(ZoneId.systemDefault()).toInstant());

        long curTime = System.currentTimeMillis();
        long regTime = new Date(paramDate.getTime()).getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;

        if (diffTime < Comment.TIME_MAXIMUM.SEC)
        {
            // sec
            msg = diffTime + "초전";
        }
        else if ((diffTime /= Comment.TIME_MAXIMUM.SEC) < Comment.TIME_MAXIMUM.MIN)
        {
            // min
            System.out.println(diffTime);

            msg = diffTime + "분전";
        }
        else if ((diffTime /= Comment.TIME_MAXIMUM.MIN) < Comment.TIME_MAXIMUM.HOUR)
        {
            // hour
            msg = (diffTime ) + "시간전";
        }
        else if ((diffTime /= Comment.TIME_MAXIMUM.HOUR) < Comment.TIME_MAXIMUM.DAY)
        {
            // day
            msg = (diffTime ) + "일전";
        }
        else if ((diffTime /= Comment.TIME_MAXIMUM.DAY) < Comment.TIME_MAXIMUM.MONTH)
        {
            // day
            msg = (diffTime ) + "달전";
        }
        else
        {
            msg = (diffTime) + "년전";
        }

        return msg;
    }
}
