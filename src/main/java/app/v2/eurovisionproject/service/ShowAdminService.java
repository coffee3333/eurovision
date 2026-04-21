package app.v2.eurovisionproject.service;

import app.v2.eurovisionproject.dto.ScoreEntry;
import app.v2.eurovisionproject.entities.Participation;
import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.entities.Song;
import app.v2.eurovisionproject.repositories.ParticipationRepository;
import app.v2.eurovisionproject.repositories.ShowRepository;
import app.v2.eurovisionproject.repositories.SongRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowAdminService {

    private final ShowRepository showRepo;
    private final ParticipationRepository participationRepo;
    private final SongRepository songRepo;
    private final VoteService voteService;

    public ShowAdminService(ShowRepository showRepo,
                            ParticipationRepository participationRepo,
                            SongRepository songRepo,
                            @Lazy VoteService voteService) {
        this.showRepo = showRepo;
        this.participationRepo = participationRepo;
        this.songRepo = songRepo;
        this.voteService = voteService;
    }

    public Show startVoting(int showId) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));
        if (show.isVotingOpen()) {
            throw new IllegalStateException("Voting is already open for: " + show.getName());
        }
        boolean anyOpen = showRepo.findAll().stream().anyMatch(Show::isVotingOpen);
        if (anyOpen) {
            throw new IllegalStateException("Another show is already open. Stop it before starting a new one.");
        }
        show.setVotingOpen(true);
        return showRepo.save(show);
    }

    public Show stopVoting(int showId) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));
        if (!show.isVotingOpen()) {
            throw new IllegalStateException("Voting is not open for: " + show.getName());
        }
        show.setVotingOpen(false);
        showRepo.save(show);

        String typeName = show.getShowType().getName();
        if ("SEMI_FINAL_1".equals(typeName) || "SEMI_FINAL_2".equals(typeName)) {
            qualifyTopTen(show);
        }

        return show;
    }

    private void qualifyTopTen(Show semiFinal) {
        Show grandFinal = showRepo.findByShowTypeName("GRAND_FINAL")
                .orElseThrow(() -> new IllegalStateException("Grand Final show not found"));

        List<ScoreEntry> scores = voteService.getResults(semiFinal.getId());

        scores.stream()
                .limit(10)
                .forEach(entry -> {
                    Song song = songRepo.findById(entry.getSongId())
                            .orElseThrow(() -> new IllegalStateException("Song not found: " + entry.getSongId()));
                    if (!participationRepo.existsByShowAndSong(grandFinal, song)) {
                        Participation p = new Participation();
                        p.setSong(song);
                        p.setShow(grandFinal);
                        participationRepo.save(p);
                    }
                });
    }
}
