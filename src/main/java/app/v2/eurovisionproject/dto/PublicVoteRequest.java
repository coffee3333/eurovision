package app.v2.eurovisionproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicVoteRequest {

    private int voterUserId;
    private int fromCountryId;
    private int toSongId;
    private int showId;
}
