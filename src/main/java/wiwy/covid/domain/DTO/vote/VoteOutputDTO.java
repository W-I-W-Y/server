package wiwy.covid.domain.DTO.vote;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.domain.Vote;

@Getter
public class VoteOutputDTO {

    private Long voteId;
    private String content;
    private int agreeCnt;
    private int disagreeCnt;
    private String createDate;

    protected VoteOutputDTO() {
    }

    public VoteOutputDTO(Vote vote) {
        this.voteId = vote.getId();
        this.content = vote.getContent();
        this.agreeCnt = vote.getAgree();
        this.disagreeCnt = vote.getDisagree();
        this.createDate = vote.makeDate();
    }
}
