package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.CountryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryTypeRepository extends JpaRepository<CountryType, Integer> {

    Optional<CountryType> findByName(String name);
}
