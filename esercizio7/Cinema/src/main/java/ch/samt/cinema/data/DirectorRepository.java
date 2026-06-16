package ch.samt.cinema.data;

import ch.samt.cinema.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository
        extends JpaRepository<Director, Long> {
    Director findById(long id);
}
