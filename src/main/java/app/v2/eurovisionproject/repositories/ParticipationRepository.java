package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.Participation;
import app.v2.eurovisionproject.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    List<Participation> findByShow(Show show);
}
