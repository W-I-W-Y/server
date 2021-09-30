package wiwy.covid.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class LikePost {

    @Id @GeneratedValue
    private Long id;

    private Long memberId;
    private Long postId;

    protected LikePost() {

    }

    public LikePost(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
