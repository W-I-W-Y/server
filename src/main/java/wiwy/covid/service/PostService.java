package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

//    @Transactional
//    public Long post(Board board, Post p) {
//        Post newPost = Post.makePost(board, p.getPostName(), p.getContent());
//        postRepository.save(newPost);
//        return newPost.getId();
//    }
//
//
//
//    public Page<Post> pagingPosts (Long boardId, int page, int perPageNum) {
//        PageRequest request = PageRequest.of(page, perPageNum, Sort.by("createTime").descending());
//        return postRepository.findByBoardId(boardId, request);
//    }


}
