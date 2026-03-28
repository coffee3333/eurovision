package app.v2.eurovisionproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "public_vote",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_public_user_show",
            columnNames = {"voter_user_id", "show_id"}
        )
    }
)
public class PublicVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "voter_user_id")
    private User voterUser;

    @ManyToOne
    @JoinColumn(name = "from_country_id")
    private Country fromCountry;

    @ManyToOne
    @JoinColumn(name = "to_song_id")
    private Song toSong;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
