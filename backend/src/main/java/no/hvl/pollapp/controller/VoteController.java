package no.hvl.pollapp.controller;

import no.hvl.pollapp.domain.Vote;
import no.hvl.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polls")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {

    private final PollManager pollManager;

    public VoteController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    // POST /polls/{pollId}/vote?optionId=123
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<Vote> vote(@PathVariable Long pollId,
                                     @RequestParam Long optionId,
                                     @AuthenticationPrincipal Jwt jwt) {
        // Prefer a stable username from token
        String username = jwt.getClaimAsString("preferred_username");
        if (username == null || username.isBlank()) {
            username = jwt.getSubject(); // fallback to sub
        }
        Vote saved = pollManager.vote(optionId, username);
        return ResponseEntity.ok(saved);
    }
}
