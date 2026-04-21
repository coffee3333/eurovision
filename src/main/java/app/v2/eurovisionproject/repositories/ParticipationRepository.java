package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.Country;
import app.v2.eurovisionproject.entities.Participation;
import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    List<Participation> findByShow(Show show);

    boolean existsByShowAndSong(Show show, Song song);

    @Query("SELECT COUNT(p) > 0 FROM Participation p WHERE p.show = :show AND p.song.country = :country")
    boolean existsByShowAndCountry(@Param("show") Show show, @Param("country") Country country);
}
