package no.hvl.pollapp.repository;

import no.hvl.pollapp.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {}
