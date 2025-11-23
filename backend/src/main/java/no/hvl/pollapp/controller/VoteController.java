package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.User;
import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.domain.VoteOption;
import no.hvl.pollapp.repository.UserRepository;
import no.hvl.pollapp.repository.VoteOptionRepository;
import no.hvl.pollapp.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {

    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final UserRepository userRepository;

    public VoteController(VoteRepository voteRepository,
                          VoteOptionRepository voteOptionRepository,
                          UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/polls/{pollId}/vote")
    public ResponseEntity<?> voteOnPoll(
            @PathVariable Long pollId,
            @RequestParam Long optionId,
            @RequestParam String username
    ) {
        System.out.println("Vote request: pollId=" + pollId + ", optionId=" + optionId + ", username=" + username);

        try {
            // 1️⃣ Find or create the user based on username from Keycloak
            User user = userRepository.findByUsername(username)
                    .orElseGet(() -> {
                        System.out.println("Creating new user: " + username);
                        User newUser = new User(username);
                        return userRepository.save(newUser);
                    });

            // 2️⃣ Validate option
            Optional<VoteOption> optionOpt = voteOptionRepository.findById(optionId);
            if (optionOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Vote option not found: " + optionId);
            }

            VoteOption option = optionOpt.get();

            // 3️⃣ Ensure option belongs to this poll
            if (!option.getPoll().getId().equals(pollId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Option does not belong to poll " + pollId);
            }

            // 4️⃣ Prevent double voting in same poll
            boolean alreadyVoted = voteRepository.existsByUser_IdAndOption_Poll_Id(user.getId(), pollId);
            if (alreadyVoted) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User '" + username + "' has already voted on this poll.");
            }

            // 5️⃣ Save vote
            Vote vote = new Vote(user, option);
            Vote saved = voteRepository.save(vote);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering vote");
        }
    }
}
