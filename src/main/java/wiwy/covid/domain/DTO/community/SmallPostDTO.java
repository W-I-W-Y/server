package wiwy.covid.domain.DTO.community;

import lombok.Getter;
import wiwy.covid.domain.Post;

@Getter
public class SmallPostDTO {

    private String calculateTime;
    private String postName;

    public SmallPostDTO(Post post) {
        this.calculateTime = post.calculateTime(post.getCreateTime());
        this.postName = post.getPostName();
    }
}
