package app.v2.eurovisionproject.dto;

import lombok.Getter;

@Getter
public class ScoreEntry {

    private int songId;
    private String title;
    private String country;
    private int juryPoints;
    private int publicPoints;
    private int totalPoints;

    public ScoreEntry(int songId, String title, String country, int juryPoints, int publicPoints) {
        this.songId = songId;
        this.title = title;
        this.country = country;
        this.juryPoints = juryPoints;
        this.publicPoints = publicPoints;
        this.totalPoints = juryPoints + publicPoints;
    }
}
