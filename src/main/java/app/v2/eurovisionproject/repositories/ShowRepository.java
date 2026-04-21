package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Query("SELECT s FROM Show s WHERE s.showType.name = :typeName")
    Optional<Show> findByShowTypeName(@Param("typeName") String typeName);

    Optional<Show> findByVotingOpenTrue();
}
