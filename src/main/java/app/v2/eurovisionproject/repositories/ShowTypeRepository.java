package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.ShowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ShowTypeRepository extends JpaRepository<ShowType, Integer> {

    Optional<ShowType> findByName(String name);
}
