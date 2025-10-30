package no.hvl.pollapp.service;

import no.hvl.pollapp.domain.Poll;
import no.hvl.pollapp.domain.User;
import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.domain.VoteOption;
import no.hvl.pollapp.repository.PollRepository;
import no.hvl.pollapp.repository.UserRepository;
import no.hvl.pollapp.repository.VoteOptionRepository;
import no.hvl.pollapp.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PollManager {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteOptionRepository voteOptionRepository;

    public PollManager(PollRepository pollRepository,
                       VoteRepository voteRepository,
                       UserRepository userRepository,
                       VoteOptionRepository voteOptionRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.voteOptionRepository = voteOptionRepository;
    }

    @Transactional
    public Poll createPoll(Poll poll) {
        // Connect child options -> parent poll before saving
        if (poll.getOptions() != null) {
            poll.getOptions().forEach(opt -> opt.setPoll(poll));
        }
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll getPollOrThrow(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Poll not found: " + id));
    }

    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }

    @Transactional
    public Vote registerVote(Long pollId, Long optionId, String username) {
        // ensure poll exists
        Poll poll = getPollOrThrow(pollId);

        // ensure option belongs to that poll
        VoteOption option = voteOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("Vote option not found: " + optionId));
        if (option.getPoll() == null || !option.getPoll().getId().equals(poll.getId())) {
            throw new IllegalArgumentException("Option " + optionId + " does not belong to poll " + pollId);
        }

        // find or create user
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username)));

        // Save vote
        Vote vote = new Vote(user, option);
        return voteRepository.save(vote);
    }

    public List<Vote> getVotesForPoll(Long pollId) {
        // simple filter; you can add a custom query later for efficiency
        return voteRepository.findAll().stream()
                .filter(v -> v.getOption() != null
                        && v.getOption().getPoll() != null
                        && pollId.equals(v.getOption().getPoll().getId()))
                .toList();
    }
}