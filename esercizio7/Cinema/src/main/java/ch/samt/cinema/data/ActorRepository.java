package ch.samt.cinema.data;

import ch.samt.cinema.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository
        extends JpaRepository<Actor, Long> {
    Actor findById(long id);
}
