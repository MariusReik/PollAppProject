package no.hvl.pollapp.repository;

import no.hvl.pollapp.domain.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {
}
