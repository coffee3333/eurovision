package app.v2.eurovisionproject.repositories;

import app.v2.eurovisionproject.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByCode(String code);
    Optional<Country> findByName(String name);
}
