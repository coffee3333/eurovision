package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.Song;
import app.v2.eurovisionproject.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    Optional<Song> findByCountry(Country country);
}
