package ch.samt.videogames.data;

import ch.samt.videogames.domain.GameProfile;
import ch.samt.videogames.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository
        extends JpaRepository<Player, Long> {
    Player findById(long id);
}
