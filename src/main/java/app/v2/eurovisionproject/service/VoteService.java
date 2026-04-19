package app.v2.eurovisionproject.service;

import app.v2.eurovisionproject.dto.JuryVoteRequest;
import app.v2.eurovisionproject.dto.PublicVoteRequest;
import app.v2.eurovisionproject.dto.ScoreEntry;
import app.v2.eurovisionproject.entities.*;
import app.v2.eurovisionproject.repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final JuryVoteRepository juryVoteRepo;
    private final PublicVoteRepository publicVoteRepo;
    private final UserRepository userRepo;
    private final SongRepository songRepo;
    private final ShowRepository showRepo;
    private final ParticipationRepository participationRepo;

    public VoteService(JuryVoteRepository juryVoteRepo,
                       PublicVoteRepository publicVoteRepo,
                       UserRepository userRepo,
                       SongRepository songRepo,
                       ShowRepository showRepo,
                       ParticipationRepository participationRepo) {
        this.juryVoteRepo = juryVoteRepo;
        this.publicVoteRepo = publicVoteRepo;
        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.showRepo = showRepo;
        this.participationRepo = participationRepo;
    }

    public void submitJuryVotes(JuryVoteRequest request, int userId) {
        User jury = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!"JURY".equals(jury.getRole().getName())) {
            throw new IllegalArgumentException("Only JURY users can submit jury votes");
        }

        Country fromCountry = jury.getCountry();

        Show show = showRepo.findById(request.getShowId())
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + request.getShowId()));

        if (request.getVotes() == null || request.getVotes().size() != 10) {
            throw new IllegalArgumentException("Jury must submit exactly 10 ranked votes");
        }

        Set<Integer> ranks = new HashSet<>();
        for (JuryVoteRequest.RankedVote rv : request.getVotes()) {
            if (rv.getRank() < 1 || rv.getRank() > 10) {
                throw new IllegalArgumentException("Rank must be between 1 and 10, got: " + rv.getRank());
            }
            if (!ranks.add(rv.getRank())) {
                throw new IllegalArgumentException("Duplicate rank: " + rv.getRank());
            }
        }

        for (JuryVoteRequest.RankedVote rv : request.getVotes()) {
            Song toSong = songRepo.findById(rv.getToSongId())
                    .orElseThrow(() -> new IllegalArgumentException("Song not found: " + rv.getToSongId()));

            JuryVote vote = new JuryVote();
            vote.setJuryUser(jury);
            vote.setFromCountry(fromCountry);
            vote.setToSong(toSong);
            vote.setShow(show);
            vote.setRank(rv.getRank());

            juryVoteRepo.save(vote);
        }
    }

    public void submitPublicVote(PublicVoteRequest request, int userId) {
        User voter = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!"PUBLIC".equals(voter.getRole().getName())) {
            throw new IllegalArgumentException("Only PUBLIC users can submit public votes");
        }

        Country fromCountry = voter.getCountry();

        Song toSong = songRepo.findById(request.getToSongId())
                .orElseThrow(() -> new IllegalArgumentException("Song not found: " + request.getToSongId()));

        Show show = showRepo.findById(request.getShowId())
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + request.getShowId()));

        PublicVote vote = new PublicVote();
        vote.setVoterUser(voter);
        vote.setFromCountry(fromCountry);
        vote.setToSong(toSong);
        vote.setShow(show);

        publicVoteRepo.save(vote);
    }

    public List<ScoreEntry> getResults(int showId) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));

        List<Participation> participations = participationRepo.findByShow(show);
        List<Song> songs = participations.stream()
                .map(Participation::getSong)
                .collect(Collectors.toList());

        Map<Integer, Integer> juryPoints = calculateJuryPoints(show, songs);
        Map<Integer, Integer> publicPoints = calculatePublicPoints(show, songs);

        return songs.stream()
                .map(song -> new ScoreEntry(
                        song.getId(),
                        song.getTitle(),
                        song.getCountry().getName(),
                        juryPoints.getOrDefault(song.getId(), 0),
                        publicPoints.getOrDefault(song.getId(), 0)
                ))
                .sorted(Comparator.comparingInt(ScoreEntry::getTotalPoints).reversed())
                .collect(Collectors.toList());
    }

    private int rankToPoints(int rank) {
        return switch (rank) {
            case 1  -> 12;
            case 2  -> 10;
            case 3  -> 8;
            case 4  -> 7;
            case 5  -> 6;
            case 6  -> 5;
            case 7  -> 4;
            case 8  -> 3;
            case 9  -> 2;
            case 10 -> 1;
            default -> 0;
        };
    }

    private Map<Integer, Integer> calculateJuryPoints(Show show, List<Song> songs) {
        Map<Integer, Integer> points = new HashMap<>();
        List<JuryVote> allVotes = juryVoteRepo.findByShow(show);

        for (JuryVote vote : allVotes) {
            int songId = vote.getToSong().getId();
            int pts = rankToPoints(vote.getRank());
            points.merge(songId, pts, Integer::sum);
        }

        return points;
    }

    private Map<Integer, Integer> calculatePublicPoints(Show show, List<Song> songs) {
        Map<Integer, Long> voteCounts = new HashMap<>();
        for (Song song : songs) {
            long count = publicVoteRepo.countByToSongAndShow(song, show);
            voteCounts.put(song.getId(), count);
        }

        List<Map.Entry<Integer, Long>> ranked = new ArrayList<>(voteCounts.entrySet());
        ranked.sort(Map.Entry.<Integer, Long>comparingByValue().reversed());

        Map<Integer, Integer> points = new HashMap<>();
        int[] pointScale = {12, 10, 8, 7, 6, 5, 4, 3, 2, 1};

        for (int i = 0; i < Math.min(ranked.size(), 10); i++) {
            int songId = ranked.get(i).getKey();
            long votes = ranked.get(i).getValue();
            if (votes > 0) {
                points.put(songId, pointScale[i]);
            }
        }

        return points;
    }
}
