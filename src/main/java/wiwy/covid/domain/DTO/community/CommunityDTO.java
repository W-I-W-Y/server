package wiwy.covid.domain.DTO.community;

import lombok.Getter;
import lombok.Setter;
import wiwy.covid.domain.DTO.vote.VoteOutputDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommunityDTO {

    private Long boardId;
    private String boardName;
    private List<SmallPostDTO> smallPostDTOS = new ArrayList<>();
    private VoteOutputDTO voteOutputDTO;
}
