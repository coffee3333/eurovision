package app.v2.eurovisionproject.dto;

import app.v2.eurovisionproject.entities.Song;
import lombok.Getter;

@Getter
public class SongResponse {

    private final int id;
    private final String title;
    private final String artistName;
    private final String countryName;
    private final String countryCode;

    public SongResponse(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.artistName = song.getArtist().getUsername();
        this.countryName = song.getCountry().getName();
        this.countryCode = song.getCountry().getCode();
    }
}
