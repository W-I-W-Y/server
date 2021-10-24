package wiwy.covid.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class DisagreeVote {

    @Id @GeneratedValue
    private Long id;

    private Long memberId;
    private Long voteId;

    protected DisagreeVote() {
    }

    public DisagreeVote(Long memberId, Long voteId) {
        this.memberId = memberId;
        this.voteId = voteId;
    }
}
