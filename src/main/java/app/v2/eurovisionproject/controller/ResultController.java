package app.v2.eurovisionproject.controller;

import app.v2.eurovisionproject.dto.ScoreEntry;
import app.v2.eurovisionproject.dto.SongResponse;
import app.v2.eurovisionproject.entities.Country;
import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.entities.Song;
import app.v2.eurovisionproject.repositories.ParticipationRepository;
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
    private final ParticipationRepository participationRepo;

    public ResultController(VoteService voteService, ShowRepository showRepo, SongRepository songRepo,
                            ParticipationRepository participationRepo) {
        this.voteService = voteService;
        this.showRepo = showRepo;
        this.songRepo = songRepo;
        this.participationRepo = participationRepo;
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
    public ResponseEntity<List<SongResponse>> getAllSongs() {
        List<SongResponse> songs = songRepo.findAll().stream()
                .map(SongResponse::new)
                .toList();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/shows/{id}/songs")
    public ResponseEntity<?> getSongsForShow(@PathVariable int id) {
        Show show = showRepo.findById(id).orElse(null);
        if (show == null) {
            return ResponseEntity.notFound().build();
        }
        List<SongResponse> songs = participationRepo.findByShow(show).stream()
                .map(p -> new SongResponse(p.getSong()))
                .toList();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/shows/{id}/countries")
    public ResponseEntity<?> getCountriesForShow(@PathVariable int id) {
        Show show = showRepo.findById(id).orElse(null);
        if (show == null) {
            return ResponseEntity.notFound().build();
        }
        List<Country> countries = participationRepo.findByShow(show).stream()
                .map(p -> p.getSong().getCountry())
                .distinct()
                .toList();
        return ResponseEntity.ok(countries);
    }
}
