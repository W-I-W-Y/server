package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class HateComment {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private Long commentId;

    protected HateComment() {
    }

    public HateComment(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
