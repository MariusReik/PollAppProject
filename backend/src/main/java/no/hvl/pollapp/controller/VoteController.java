package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.User;
import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.domain.VoteOption;
import no.hvl.pollapp.repository.UserRepository;
import no.hvl.pollapp.repository.VoteRepository;
import no.hvl.pollapp.repository.VoteOptionRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {

    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final UserRepository userRepository;

    public VoteController(VoteRepository voteRepository, VoteOptionRepository voteOptionRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestParam Long userId, @RequestParam Long optionId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            Optional<VoteOption> option = voteOptionRepository.findById(optionId);

            if (user.isEmpty() || option.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Vote vote = new Vote(user.get(), option.get());
            Vote saved = voteRepository.save(vote);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
