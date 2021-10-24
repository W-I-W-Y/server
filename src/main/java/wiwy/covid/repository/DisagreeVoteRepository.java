package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.DisagreeVote;

import java.util.List;

public interface DisagreeVoteRepository extends JpaRepository<DisagreeVote, Long> {

    List<DisagreeVote> findByVoteId(Long voteId);
}
