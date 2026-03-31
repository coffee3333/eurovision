package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.JuryVote;
import app.v2.eurovisionproject.entities.Country;
import app.v2.eurovisionproject.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JuryVoteRepository extends JpaRepository<JuryVote, Integer> {

    List<JuryVote> findByShow(Show show);

    List<JuryVote> findByFromCountryAndShow(Country country, Show show);
}
