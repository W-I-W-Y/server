package wiwy.covid.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wiwy.covid.domain.*;
import wiwy.covid.domain.DTO.vote.VoteInputDTO;
import wiwy.covid.domain.DTO.vote.VoteOutputDTO;
import wiwy.covid.repository.AgreeVoteRepository;
import wiwy.covid.repository.DisagreeVoteRepository;
import wiwy.covid.repository.VoteRepository;
import wiwy.covid.service.MemberService;

import javax.swing.undo.CannotUndoException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VoteController {

    private final MemberService memberService;
    private final VoteRepository voteRepository;
    private final AgreeVoteRepository agreeVoteRepository;
    private final DisagreeVoteRepository disagreeVoteRepository;

    // 투표 추가
    @PostMapping("/api/vote/add")
    public String addVote(@RequestBody VoteInputDTO voteInputDTO) {
        Vote vote = new Vote(voteInputDTO);
        voteRepository.save(vote);
        return "addVote";
    }

    // 투표 수정
    @PostMapping("/api/vote/modify/{voteId}")
    public String modifyVote(@PathVariable Long voteId, @RequestBody VoteInputDTO voteInputDTO) {
        Optional<Vote> findVote = voteRepository.findById(voteId);
        if (findVote.isEmpty()) {
            throw new IllegalStateException("modifyVote : 존재하지 않는 투표입니다.");
        }
        Vote vote = findVote.get();
        Vote modifiedVote = vote.modifyContent(voteInputDTO.getContent());
        voteRepository.save(modifiedVote);
        return "modifyVote";
    }

    // 투표 삭제
    @PostMapping("/api/vote/delete/{voteId}")
    public String deleteVote(@PathVariable Long voteId) {
        Optional<Vote> findVote = voteRepository.findById(voteId);
        if (findVote.isEmpty()) {
            throw new IllegalStateException("deleteVote : 존재하지 않는 투표입니다.");
        }
        Vote vote = findVote.get();
        voteRepository.delete(vote);
        return "deleteVote";
    }

    // 투표 동의
    @PatchMapping("/api/vote/agree/{voteId}")
    public String agreeVote(@PathVariable Long voteId, Authentication authentication) {
        Optional<Vote> findVote = voteRepository.findById(voteId);
        if (findVote.isEmpty()) {
            throw new IllegalStateException("agreeVote : 존재하지 않는 투표입니다.");
        }
        try {
            Member member = memberService.getMemberFromToken(authentication);
            Vote vote = findVote.get();
            // 비동의를 눌렀는지 확인
            List<DisagreeVote> findDV = disagreeVoteRepository.findByVoteId(vote.getId());
            for (DisagreeVote disagreeVote : findDV) {
                if (disagreeVote.getMemberId().equals(member.getId())) {
                    // 해당 유저가 투표에 비동의를 한 적이 있는 경우
                    disagreeVoteRepository.delete(disagreeVote);
                    vote.minusDisagree();
                }
            }

            // 1. 이 투표에 동의가 있는지 확인
            List<AgreeVote> findAV = agreeVoteRepository.findByVoteId(vote.getId());
            if (findAV == null || findAV.isEmpty()) { // 동의가 없는 투표일 때
                AgreeVote agreeVote = new AgreeVote(member.getId(), vote.getId());
                agreeVoteRepository.save(agreeVote);
            } else { // 동의가 있는 투표일 때
                for (AgreeVote av : findAV) {
                    if (av.getMemberId().equals(member.getId())) {
                        agreeVoteRepository.delete(av);
                        vote.minusAgree();
                        voteRepository.save(vote);
                        return "cancelAgree";
                    }
                }
                // 동의 누름
                AgreeVote agreeVote = new AgreeVote(member.getId(), vote.getId());
                agreeVoteRepository.save(agreeVote);
            }
            vote.plusAgree();
            voteRepository.save(vote);
            return "submitAgree";

        } catch (NullPointerException e) {
            return "nonMember";
        }

    }

    // 투표 비동의
    @PatchMapping("/api/vote/disagree/{voteId}")
    public String disagreeVote(@PathVariable Long voteId, Authentication authentication) {
        Optional<Vote> findVote = voteRepository.findById(voteId);
        if (findVote.isEmpty()) {
            throw new IllegalStateException("disagreeVote : 존재하지 않는 투표입니다.");
        }
        try{
            Member member = memberService.getMemberFromToken(authentication);
            Vote vote = findVote.get();

            // 이미 동의를 누른 투표일 경우
            List<AgreeVote> findAV = agreeVoteRepository.findByVoteId(vote.getId());
            for (AgreeVote agreeVote : findAV) {
                if (agreeVote.getMemberId().equals(member.getId())) {
                    // 해당 유저가 이미 동의를 한 투표인 경우
                    agreeVoteRepository.delete(agreeVote);
                    vote.minusAgree();
                }
            }
            // 1. 이 투표에 비동의가 있는지 확인
            List<DisagreeVote> findDV = disagreeVoteRepository.findByVoteId(vote.getId());
            if (findDV == null || findDV.isEmpty()) { // 비동의가 없는 투표 일 때
                DisagreeVote disagreeVote = new DisagreeVote(member.getId(), vote.getId());
                disagreeVoteRepository.save(disagreeVote);
            } else { // 비동의가 있는 투표 일 때
                for (DisagreeVote dv : findDV) {
                    if (dv.getMemberId().equals(member.getId())) { // 이미 비동의를 누른 투표일 때
                        disagreeVoteRepository.delete(dv);
                        vote.minusDisagree();
                        voteRepository.save(vote);
                        return "cancelDisagree";
                    }

                }

                // 비동의 누름
                DisagreeVote disagreeVote = new DisagreeVote(member.getId(), vote.getId());
                disagreeVoteRepository.save(disagreeVote);
            }
            vote.plusDisagree();
            voteRepository.save(vote);
            return "submitDisagree";

        } catch (NullPointerException e) {
            return "nonMember";
        }

    }

    // 투표 보기
    @GetMapping("/api/vote/view")
    public VoteOutputDTO viewVote() {
//        Member member = memberService.getMemberFromToken(authentication);
        Optional<Vote> findVote = voteRepository.findFirstByOrderByIdDesc();
        if (findVote.isEmpty()) {
            throw new IllegalStateException("viewVote : 투표가 존재하지 않습니다.");
        }

        Vote vote = findVote.get();
        VoteOutputDTO voteOutputDTO = new VoteOutputDTO(vote);
        return voteOutputDTO;
//        List<AgreeVote> findAV = agreeVoteRepository.findByVoteId(vote.getId());
//        for (AgreeVote av : findAV) {
//            if (av.getMemberId().equals(member.getId())) {
//                // 이미 동의한 투표
//                voteOutputDTO.setAgree(true);
//            } else {
//                voteOutputDTO.setAgree(false);
//            }
//        }
//        List<DisagreeVote> findDV = disagreeVoteRepository.findByVoteId(vote.getId());
//        for (DisagreeVote disagreeVote : findDV) {
//            if (disagreeVote.getMemberId().equals(member.getId())) {
//                voteOutputDTO.setDisagree(true);
//            } else {
//                voteOutputDTO.setDisagree(false);
//            }
//        }
//        return voteOutputDTO;
    }

}
