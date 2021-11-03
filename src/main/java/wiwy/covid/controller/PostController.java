package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.*;
import wiwy.covid.domain.DTO.comment.CommentOutputDTO;
import wiwy.covid.domain.DTO.post.PostAndCommentDTO;
import wiwy.covid.domain.DTO.post.PostInputDTO;
import wiwy.covid.domain.DTO.post.PostOutputDTO;
import wiwy.covid.paging.CustomPagination;
import wiwy.covid.repository.*;
import wiwy.covid.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final MemberService memberService;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final HatePostRepository hatePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final HateCommentRepository hateCommentRepository;


    // 특정 포스트 보기
    @GetMapping("/api/post/view/{postId}")
    public PostAndCommentDTO viewPost(@PathVariable Long postId, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<LikePost> isLike = likePostRepository.findByPostIdAndMemberId(postId, member.getId());
        Optional<HatePost> isHate = hatePostRepository.findByPostIdAndMemberId(postId, member.getId());

        Optional<Post> findPost = postRepository.findById(postId);
        PostAndCommentDTO pac = new PostAndCommentDTO();
        if (findPost.isPresent()) {
            PostOutputDTO post = new PostOutputDTO(findPost.get());
            List<Comment> comments = commentRepository.findByPost(findPost.get());
            List<CommentOutputDTO> collect = comments.stream().map(comment -> new CommentOutputDTO(comment)).collect(Collectors.toList());
            for (CommentOutputDTO commentOutputDTO : collect) {
                if (member.getId().equals(commentOutputDTO.getMemberId())) {
                    commentOutputDTO.setAuthor(true);
                }
                Optional<LikeComment> commentLike = likeCommentRepository.findByCommentIdAndMemberId(commentOutputDTO.getId(), member.getId());
                Optional<HateComment> commentHate = hateCommentRepository.findByCommentIdAndMemberId(commentOutputDTO.getId(), member.getId());
                if (commentLike.isPresent()) {
                    commentOutputDTO.setLike(true);
                }
                if (commentHate.isPresent()) {
                    commentOutputDTO.setHate(true);
                }
            }
            if (isLike.isPresent()) {
                pac.setLike(true);
            }
            if (isHate.isPresent()) {
                pac.setHate(true);
            }
            if (member.getId().equals(findPost.get().getMember().getId())) {
                pac.setAuthor(true);
            }
            pac.setBoardId(findPost.get().getBoard().getId());
            pac.setPostOutputDTO(post);
            pac.setCommentOutputDTOS(collect);
            return pac;
        } else {
            throw new IllegalStateException("viewPost : 존재하지 않는 게시글입니다.");
        }
    }

    // 한 유저가 작성한 포스트 전체 보기의 페이지네이션 정보 주기
    @GetMapping("/api/post/viewByMember/pagination")
    public CustomPagination viewByMemberPagination(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
        Page<Post> posts = postRepository.findByMember(member, pageable);
        return new CustomPagination(posts.getTotalElements(), posts.getTotalPages());
    }

    // 한 유저가 작성한 포스트 전체 보기
    @GetMapping("/api/post/viewByMember/{pageNum}")
    public List<PostOutputDTO> viewPostByMember(Authentication authentication, @PathVariable Integer pageNum) {
        Member findMember = memberService.getMemberFromToken(authentication);
        if (pageNum == 1) {
            Pageable pageable = PageRequest.of(0, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByMember(findMember, pageable);
            List<PostOutputDTO> collect = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return collect;
        } else {
            Pageable pageable = PageRequest.of(pageNum-1, 15, Sort.by("createTime").descending());
            Page<Post> posts = postRepository.findByMember(findMember, pageable);
            List<PostOutputDTO> collect = posts.stream().map(post -> new PostOutputDTO(post)).collect(Collectors.toList());
            return collect;
        }
    }

    // 지역 게시판에 포스트 작성
    @PostMapping("/api/post/add/region")
    public String addRegionPost(@RequestBody PostInputDTO postDTO, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Board> findBoard = boardRepository.findByBoardName(member.getRegion());
        postRepository.save(new Post(postDTO, member, findBoard.get()));
        return "addRegionPost";
    }

    // 해당 게시판에 포스트 작성
    @PostMapping("/api/post/add/{boardId}")
    public String addPost(@PathVariable Long boardId, @RequestBody PostInputDTO postDTO, Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Board> findBoard = boardRepository.findById(boardId);
        postRepository.save(new Post(postDTO, member, findBoard.get()));
        return "addPost";
    }

    // 작성한 포스트 수정
    @PatchMapping("/api/post/modify/{postId}")
    public String modifyPost(@PathVariable Long postId, Authentication authentication, @RequestBody PostInputDTO postInputDTO) {
        Member member = memberService.getMemberFromToken(authentication);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("modifyPost : 존재하지 않는 게시글입니다.");
        }
        if (member.getId().equals(findPost.get().getMember().getId())) {
            Post modifiedPost = findPost.get().modify(postInputDTO);
            postRepository.save(modifiedPost);
        } else {
            throw new IllegalStateException("modifyPost : 본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        return "modifyPost";
    }

    // 포스트 삭제
    @PostMapping("/api/post/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);

        if (findPost.isPresent()) {
            List<LikePost> likePosts = likePostRepository.findByPostId(postId);
            List<HatePost> hatePosts = hatePostRepository.findByPostId(postId);
            List<Long> likeIds = new ArrayList<>();
            List<Long> hateIds = new ArrayList<>();
            for (HatePost hatePost : hatePosts) {
                hateIds.add(hatePost.getId());
            }
            for (LikePost likePost : likePosts) {
                likeIds.add(likePost.getId());
            }
            postRepository.deleteById(postId);
            likePostRepository.deleteAllByIdInQuery(likeIds);
            hatePostRepository.deleteAllByIdInQuery(hateIds);
            return "deletePost";
        } else {
            throw new IllegalStateException("deletePost : 존재하지 않는 게시글입니다.");
        }
    }

    // 게시글 좋아요
    @PatchMapping("/api/post/like/{postId}")
    public String likePost(@PathVariable Long postId, Authentication authentication) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("likePost : 존재하지 않는 게시글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);
        Post post = findPost.get();

        // 1. 이 게시글에 좋아요가 있는지 확인
        List<LikePost> findLP = likePostRepository.findByPostId(post.getId());
        if (findLP == null || findLP.isEmpty()) { // 좋아요가 없는 게시글 일 때
            LikePost likePost = new LikePost(member.getId(), post.getId());
            likePostRepository.save(likePost);
        } else { // 좋아요가 있는 게시글 일 때
            for (LikePost lp : findLP) {
                if (lp.getMemberId().equals(member.getId())) { // 이미 좋아요를 누른 게시글 일 때 -> 좋아요 취소

                    likePostRepository.delete(lp);
                    post.minusLike();
                    postRepository.save(post);
                    return "cancelLike";
                }
            }
            // 좋아요 누름
            LikePost likePost = new LikePost(member.getId(), post.getId());
            likePostRepository.save(likePost);
        }
        post.plusLike();
        postRepository.save(post);
        return "submitLike";
    }

    // 게시글 싫어요
    @PatchMapping("/api/post/hate/{postId}")
    public String hatePost(@PathVariable Long postId, Authentication authentication) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("hatePost : 존재하지 않는 게시글입니다.");
        }
        Member member = memberService.getMemberFromToken(authentication);
        Post post = findPost.get();

        // 1. 이 게시글에 싫어요가 있는지 확인
        List<HatePost> findHP = hatePostRepository.findByPostId(post.getId());
        if (findHP == null || findHP.isEmpty()) { // 싫어요가 없는 게시글 일 때
            HatePost hatePost = new HatePost(member.getId(), post.getId());
            hatePostRepository.save(hatePost);
        } else { // 싫어요가 있는 게시글 일 때
            for (HatePost hp : findHP) {
                if (hp.getMemberId().equals(member.getId())) { // 이미 싫어요를 누른 게시글 일 때 -> 싫어요 취소
                    hatePostRepository.delete(hp);
                    post.minusHate();
                    postRepository.save(post);
                    return "cancelHate";
                }
            }
            // 싫어요 누름
            HatePost hatePost = new HatePost(member.getId(), post.getId());
            hatePostRepository.save(hatePost);
        }
        post.plusHate();
        postRepository.save(post);
        return "submitHate";
    }

    // 좋아요를 누른 게시글 목록 보기 페이지네이션 정보 주기
    @GetMapping("/api/post/likeByMember/pagination")
    public CustomPagination likeByMemberPagination(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Pageable pageable = PageRequest.of(0, 15, Sort.by("id").descending());
        Page<LikePost> lps = likePostRepository.findByMemberId(member.getId(), pageable);
        return new CustomPagination(lps.getTotalElements(), lps.getTotalPages());
    }

    // 좋아요를 누른 게시글 목록 보기
    @GetMapping("/api/post/likeByMember")
    public List<PostOutputDTO> likePostByMember(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        List<LikePost> likePosts = likePostRepository.findByMemberId(member.getId());
        List<PostOutputDTO> posts = new ArrayList<>();
        for (LikePost likePost : likePosts) {
            Optional<Post> findPost = postRepository.findById(likePost.getPostId());
            if (findPost.isPresent()) {
                PostOutputDTO postOutputDTO = new PostOutputDTO(findPost.get());
                posts.add(postOutputDTO);
            }
        }
        return posts;
    }

    // 싫어요를 누른 게시글 목록 보기 페이지네이션 정보 주기
    @GetMapping("/api/post/hateByMember/pagination")
    public CustomPagination hateByMemberPagination(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        Pageable pageable = PageRequest.of(0, 15, Sort.by("id").descending());
        Page<HatePost> hps = hatePostRepository.findByMemberId(member.getId(), pageable);
        return new CustomPagination(hps.getTotalElements(), hps.getTotalPages());
    }

    // 싫어요를 누른 게시글 목록 보기
    @GetMapping("/api/post/hateByMember")
    public List<PostOutputDTO> hatePostByMember(Authentication authentication) {
        Member member = memberService.getMemberFromToken(authentication);
        List<HatePost> hatePosts = hatePostRepository.findByMemberId(member.getId());
        List<PostOutputDTO> posts = new ArrayList<>();
        for (HatePost hatePost : hatePosts) {
            Optional<Post> findPost = postRepository.findById(hatePost.getPostId());
            if (findPost.isPresent()) {
                PostOutputDTO postOutputDTO = new PostOutputDTO(findPost.get());
                posts.add(postOutputDTO);
            }
        }
        return posts;
    }
}
