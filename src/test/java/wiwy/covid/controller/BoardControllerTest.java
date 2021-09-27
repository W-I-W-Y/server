package wiwy.covid.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.MemberService;
import wiwy.covid.service.PostService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardControllerTest {

    @Autowired BoardController boardController;
    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;

    @Test
    @Transactional
    void 게시판페이징() {
        Board board = new Board();
        board.setBoardName("자유게시판");
        boardService.makeBoard(board);

//        Member member = new Member();
//        member.setAge(25);
//        member.setUserName("shin");
//        memberService.join(member);

//        for(int i=0;i<100;i++) {
//            Long post = postService.post(member.getId(), board.getId(), "hi" + i, "bye" + i);
//        }

    }

}