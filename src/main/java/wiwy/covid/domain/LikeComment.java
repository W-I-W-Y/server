package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
public class LikeComment {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private Long commentId;

    protected LikeComment() {
    }

    public LikeComment(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
