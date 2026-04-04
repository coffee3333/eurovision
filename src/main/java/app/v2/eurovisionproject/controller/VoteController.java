package app.v2.eurovisionproject.controller;

import app.v2.eurovisionproject.dto.JuryVoteRequest;
import app.v2.eurovisionproject.dto.PublicVoteRequest;
import app.v2.eurovisionproject.service.VoteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/jury")
    public ResponseEntity<?> submitJuryVotes(@RequestBody JuryVoteRequest request) {
        try {
            voteService.submitJuryVotes(request);
            return ResponseEntity.ok("Jury votes submitted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Duplicate vote: this country has already submitted jury votes for this show");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
        }
    }

    @PostMapping("/public")
    public ResponseEntity<?> submitPublicVote(@RequestBody PublicVoteRequest request) {
        try {
            voteService.submitPublicVote(request);
            return ResponseEntity.ok("Public vote submitted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Duplicate vote: this user has already voted in this show");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
        }
    }
}
