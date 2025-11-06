package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.Poll;
import no.hvl.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
@CrossOrigin(origins = "http://localhost:5173")
public class PollController {

    private record CreatePollRequest(String question, List<String> options) {}

    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public List<Poll> getAll() {
        return pollManager.listPolls();
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody CreatePollRequest req) {
        if (req == null || req.question() == null || req.question().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Poll saved = pollManager.createPoll(req.question(), req.options() == null ? List.of() : req.options());
        return ResponseEntity.ok(saved);
    }
}
