package app.v2.eurovisionproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_user_id")
    private User artist;

    @ManyToOne // why here Many to One, not one to one
    @JoinColumn(name = "country_id")
    private Country country;
}
