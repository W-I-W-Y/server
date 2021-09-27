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
import wiwy.covid.repository.PostRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long post(Board board, Post p) {
        Post newPost = Post.makePost(board, p.getPostName(), p.getContent());
        postRepository.save(newPost);
        return newPost.getId();
    }

    public List<Post> findPostsByName(String postName) {
        return postRepository.findByPostName(postName);
    }

    public List<Post> findPostsByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId);
    }

    public List<Post> findByMember(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public Post findOne(Long postId) {

        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("찾는 Post가 존재하지 않습니다.");
        }
        return findPost.get();
    }

    public List<Post> findBoards() {
        return postRepository.findAll();
    }

    public Long deletePost (Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }

    public Page<Post> pagingPosts (Long boardId, int page, int perPageNum) {
        PageRequest request = PageRequest.of(page, perPageNum, Sort.by("createTime").descending());
        return postRepository.findByBoardId(boardId, request);
    }


}
