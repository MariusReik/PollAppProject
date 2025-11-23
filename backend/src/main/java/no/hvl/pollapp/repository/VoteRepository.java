package no.hvl.pollapp.repository;

import no.hvl.pollapp.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Count how many votes a specific option has
    int countByOption_Id(Long optionId);

    // Check if a user has already voted in a given poll
    boolean existsByUser_IdAndOption_Poll_Id(Long userId, Long pollId);
}
