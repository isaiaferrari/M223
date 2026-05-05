package ch.samt.videogames.data;

import ch.samt.videogames.domain.GameProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameProfileRepository
        extends JpaRepository<GameProfile, Long> {
}
