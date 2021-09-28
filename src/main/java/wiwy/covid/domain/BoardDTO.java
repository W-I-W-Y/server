package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardDTO {

    private Page<PostDTO> posts;
}
