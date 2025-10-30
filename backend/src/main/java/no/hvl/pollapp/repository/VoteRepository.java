package no.hvl.pollapp.repository;

import no.hvl.pollapp.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VoteRepository extends JpaRepository<Vote, Long> {}