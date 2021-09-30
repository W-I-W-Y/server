package wiwy.covid.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class HatePost {

    @Id @GeneratedValue
    private Long id;

    private Long memberId;
    private Long postId;

    protected HatePost() {
    }

    public HatePost(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
