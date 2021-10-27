package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.DTO.community.CommunityDTO;
import wiwy.covid.domain.DTO.community.SmallPostDTO;
import wiwy.covid.domain.DTO.vote.VoteOutputDTO;
import wiwy.covid.domain.Post;
import wiwy.covid.domain.Vote;
import wiwy.covid.repository.BoardRepository;
import wiwy.covid.repository.PostRepository;
import wiwy.covid.repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    @GetMapping("/api/community")
    public List<CommunityDTO> community() {
        List<Board> allBoards = boardRepository.findByBoardNameContaining("게시판");
        Optional<Vote> findVote = voteRepository.findFirstByOrderByIdDesc();
        List<CommunityDTO> communityDTOList = new ArrayList<>();
        for (Board board : allBoards) {
            Pageable pageable = PageRequest.of(0, 5, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByBoard(board, pageable);
            List<SmallPostDTO> collect = posts.stream().map(post -> new SmallPostDTO(post)).collect(Collectors.toList());
            VoteOutputDTO voteOutputDTO = new VoteOutputDTO(findVote.get());
            CommunityDTO c = new CommunityDTO();
            c.setSmallPostDTOS(collect);
            c.setBoardId(board.getId());
            c.setBoardName(board.getBoardName());
            c.setVoteOutputDTO(voteOutputDTO);
            communityDTOList.add(c);
        }
        return communityDTOList;
    }


}
