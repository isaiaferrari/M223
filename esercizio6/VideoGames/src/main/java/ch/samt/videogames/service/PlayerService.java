package ch.samt.videogames.service;

import ch.samt.videogames.data.PlayerRepository;
import ch.samt.videogames.domain.Player;
import ch.samt.videogames.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player findById(long id) {return playerRepository.findById(id);}


}
