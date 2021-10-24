package wiwy.covid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wiwy.covid.domain.AgreeVote;

import java.util.List;

public interface AgreeVoteRepository extends JpaRepository<AgreeVote, Long> {
    List<AgreeVote> findByVoteId(Long voteId);
}
