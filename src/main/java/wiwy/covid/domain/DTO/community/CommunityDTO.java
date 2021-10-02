package wiwy.covid.domain.DTO.community;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommunityDTO {

    private String boardName;
    private List<SmallPostDTO> smallPostDTOS = new ArrayList<>();
}
