package no.hvl.pollapp.service;

import no.hvl.pollapp.domain.*;
import no.hvl.pollapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PollManager {

    private final PollRepository pollRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public PollManager(PollRepository pollRepository,
                       VoteOptionRepository voteOptionRepository,
                       VoteRepository voteRepository,
                       UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    public List<Poll> listPolls() {
        return pollRepository.findAll();
    }

    @Transactional
    public Poll createPoll(String question, List<String> options) {
        Poll poll = new Poll(question);
        poll = pollRepository.save(poll);
        for (String text : options) {
            voteOptionRepository.save(new VoteOption(text, poll));
        }
        return pollRepository.findById(poll.getId()).orElseThrow();
    }

    @Transactional
    public Vote vote(Long optionId, String username) {
        VoteOption option = voteOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("Option not found: " + optionId));

        User user = userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username)));

        Vote vote = new Vote(user, option);
        return voteRepository.save(vote);
    }
}
