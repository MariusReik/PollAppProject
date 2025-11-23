package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.Poll;
import no.hvl.pollapp.domain.VoteOption;
import no.hvl.pollapp.repository.PollRepository;
import no.hvl.pollapp.repository.VoteRepository;
import no.hvl.pollapp.service.PollManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PollController {

    private final PollRepository pollRepository;
    private final PollManager pollManager;
    private final VoteRepository voteRepository;

    public PollController(PollRepository pollRepository,
                          PollManager pollManager,
                          VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.pollManager = pollManager;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/polls")
    public List<Poll> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();

        // 🔢 Fill in voteCount for each option
        for (Poll poll : polls) {
            if (poll.getOptions() != null) {
                for (VoteOption option : poll.getOptions()) {
                    int count = voteRepository.countByOption_Id(option.getId());
                    option.setVoteCount(count);
                }
            }
        }

        return polls;
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        Optional<Poll> opt = pollRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Poll poll = opt.get();
        if (poll.getOptions() != null) {
            for (VoteOption option : poll.getOptions()) {
                int count = voteRepository.countByOption_Id(option.getId());
                option.setVoteCount(count);
            }
        }

        return ResponseEntity.ok(poll);
    }

    @PostMapping("/polls")
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        Poll created = pollManager.createPoll(poll);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
