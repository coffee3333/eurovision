package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.PublicVote;
import app.v2.eurovisionproject.entities.Show;
import app.v2.eurovisionproject.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicVoteRepository extends JpaRepository<PublicVote, Integer> {

    List<PublicVote> findByShow(Show show);

    long countByToSongAndShow(Song song, Show show);
}
