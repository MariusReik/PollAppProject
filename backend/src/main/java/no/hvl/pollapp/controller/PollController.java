package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.Poll;
import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.service.PollEventPublisher;
import no.hvl.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollManager pollManager;
    private final PollEventPublisher eventPublisher; // you already have this in service package

    public PollController(PollManager pollManager, PollEventPublisher eventPublisher) {
        this.pollManager = pollManager;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollManager.getAllPolls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pollManager.getPollOrThrow(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollManager.createPoll(poll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        pollManager.deletePoll(id);
        return ResponseEntity.noContent().build();
    }

    // Voting endpoint here for convenience (you also have VoteController below)
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<Vote> vote(@PathVariable Long pollId,
                                     @RequestParam Long optionId,
                                     @RequestParam String username) {
        Vote result = pollManager.registerVote(pollId, optionId, username);

        // publish with explicit pollId/optionId (no getPollId() on Vote)
        try {
            eventPublisher.publishVoteEvent("Poll " + pollId, "Option " + optionId);
        } catch (Exception ignored) {
            // don't fail the request if messaging is down
        }

        return ResponseEntity.ok(result);
    }
}
