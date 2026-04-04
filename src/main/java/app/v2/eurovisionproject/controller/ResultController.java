package app.v2.eurovisionproject.controller;

import app.v2.eurovisionproject.dto.ScoreEntry;
import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.entities.Song;
import app.v2.eurovisionproject.repositories.ShowRepository;
import app.v2.eurovisionproject.repositories.SongRepository;
import app.v2.eurovisionproject.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultController {

    private final VoteService voteService;
    private final ShowRepository showRepo;
    private final SongRepository songRepo;

    public ResultController(VoteService voteService, ShowRepository showRepo, SongRepository songRepo) {
        this.voteService = voteService;
        this.showRepo = showRepo;
        this.songRepo = songRepo;
    }

    @GetMapping("/results/{showId}")
    public ResponseEntity<?> getResults(@PathVariable int showId) {
        try {
            List<ScoreEntry> results = voteService.getResults(showId);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping("/shows")
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showRepo.findAll());
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songRepo.findAll());
    }
}
