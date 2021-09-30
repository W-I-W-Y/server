package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String postName;
    private String content;

    @CreationTimestamp
    private Timestamp createTime;

    private int viewCnt;

    private int likes;
    private int hates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    //연관관계 메서드
    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    protected Post() {
    }

    public Post(PostInputDTO postDTO, Member member, Board board) {
        this.viewCnt = 0;
        this.likes = 0;
        this.hates = 0;
        this.postName = postDTO.getPostName();
        this.content = postDTO.getContent();
        this.member = member;
        this.board = board;
    }

    public Post modify(PostInputDTO postInputDTO) {
        this.postName = postInputDTO.getPostName();
        this.content = postInputDTO.getContent();
        return this;
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

        if (diffTime < TIME_MAXIMUM.SEC)
        {
            // sec
            msg = diffTime + "초전";
        }
        else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN)
        {
            // min
            System.out.println(diffTime);

            msg = diffTime + "분전";
        }
        else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR)
        {
            // hour
            msg = (diffTime ) + "시간전";
        }
        else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY)
        {
            // day
            msg = (diffTime ) + "일전";
        }
        else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH)
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
