package app.v2.eurovisionproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JuryVoteRequest {

    private int showId;
    private List<RankedVote> votes;

    @Getter
    @Setter
    public static class RankedVote {
        private int toSongId;
        private int rank;
    }
}
