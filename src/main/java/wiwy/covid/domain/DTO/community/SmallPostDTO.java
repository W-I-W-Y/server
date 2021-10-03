package wiwy.covid.domain.DTO.community;

import lombok.Getter;
import wiwy.covid.domain.Post;

@Getter
public class SmallPostDTO {

    private Long postId;
    private String calculateTime;
    private String postName;

    public SmallPostDTO(Post post) {
        this.postId = post.getId();
        this.calculateTime = post.calculateTime(post.getCreateTime());
        this.postName = post.getPostName();
    }
}
