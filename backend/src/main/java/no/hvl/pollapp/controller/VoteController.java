package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.service.PollManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://pollapp-frontend:5173"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final PollManager pollManager;

    public VoteController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/poll/{pollId}")
    public List<Vote> getVotesByPoll(@PathVariable Long pollId) {
        return pollManager.getVotesForPoll(pollId);
    }
}
