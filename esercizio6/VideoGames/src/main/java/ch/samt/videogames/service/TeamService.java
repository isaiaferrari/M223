package ch.samt.videogames.service;

import ch.samt.videogames.data.TeamRepository;
import ch.samt.videogames.domain.Player;
import ch.samt.videogames.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team findById(long id) {return teamRepository.findById(id);}

    public Team deleteById(long id) {
        Team t = teamRepository.findById(id);
        t.setDeleted(true);
        return t;
    }
}
