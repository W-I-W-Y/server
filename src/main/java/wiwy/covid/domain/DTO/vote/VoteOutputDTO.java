package wiwy.covid.domain.DTO.vote;

import lombok.Getter;
import wiwy.covid.domain.Vote;

@Getter
public class VoteOutputDTO {

    private String content;
    private Integer agree;
    private Integer disagree;
    private String createDate;

    protected VoteOutputDTO() {
    }

    public VoteOutputDTO(Vote vote) {
        this.content = vote.getContent();
        this.agree = vote.getAgree();
        this.disagree = vote.getDisagree();
        this.createDate = vote.makeDate();
    }
}
