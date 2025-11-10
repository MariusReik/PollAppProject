package no.hvl.pollapp.service;

import no.hvl.pollapp.domain.Poll;
import no.hvl.pollapp.domain.VoteOption;
import no.hvl.pollapp.repository.PollRepository;
import no.hvl.pollapp.repository.VoteOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PollManager {

    private final PollRepository pollRepository;
    private final VoteOptionRepository voteOptionRepository;

    public PollManager(PollRepository pollRepository, VoteOptionRepository voteOptionRepository) {
        this.pollRepository = pollRepository;
        this.voteOptionRepository = voteOptionRepository;
    }

    public List<Poll> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();
        // Initialize options to avoid lazy loading issues during JSON serialization
        polls.forEach(p -> p.getOptions().size());
        return polls;
    }

    public Poll createPoll(Poll poll) {
        Poll saved = pollRepository.save(poll);
        if (poll.getOptions() != null) {
            for (VoteOption option : poll.getOptions()) {
                option.setPoll(saved);
                voteOptionRepository.save(option);
            }
        }
        return saved;
    }

    public Poll getPollById(Long id) {
        return pollRepository.findById(id).orElse(null);
    }
}
