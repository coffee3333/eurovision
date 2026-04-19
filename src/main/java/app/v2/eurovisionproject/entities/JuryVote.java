package app.v2.eurovisionproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "jury_vote",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_jury_country_show_rank",
            columnNames = {"from_country_id", "show_id", "rank"}
        ),
        @UniqueConstraint(
            name = "uq_jury_country_show_song",
            columnNames = {"from_country_id", "show_id", "to_song_id"}
        )
    }
)
public class JuryVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "jury_user_id")
    private User juryUser;

    @ManyToOne
    @JoinColumn(name = "from_country_id")
    private Country fromCountry;

    @ManyToOne
    @JoinColumn(name = "to_song_id")
    private Song toSong;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    private int rank;
}
