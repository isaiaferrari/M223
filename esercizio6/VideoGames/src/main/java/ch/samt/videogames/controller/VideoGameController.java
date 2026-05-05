package ch.samt.videogames.controller;

import ch.samt.videogames.domain.Player;
import ch.samt.videogames.domain.Team;
import ch.samt.videogames.service.GameProfileService;
import ch.samt.videogames.service.PlayerService;
import ch.samt.videogames.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class VideoGameController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final GameProfileService gameProfileService;



    @Autowired
    public VideoGameController(PlayerService playerService, TeamService teamService, GameProfileService gameProfileService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameProfileService = gameProfileService;
    };

    // READ
    @GetMapping("/players")
    public String loadPlayers(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "playerList";
    }
    @GetMapping("/teams")
    public String loadTeams(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "teamList";
    }
    @GetMapping("/gameProfiles")
    public String loadGameProfile(Model model) {
        model.addAttribute("gameProfiles", gameProfileService.findAll());
        return "gameProfileList";
    }

    // CREATE
    @GetMapping("/teams/insert")
    public String loadInsertTeamPage(@ModelAttribute Team team) {
        return "insertTeam";
    }

    @PostMapping("/teams/insert")
    public String saveTeam(@Valid Team team, Errors errors) {
        if (errors.hasErrors()) {
            return "insertTeam";
        }
        Team savedTeam = teamService.save(team);

        if (savedTeam == null) {
            throw new RuntimeException("Team not saved");
        }

        return "redirect:/teams";
    }

    @GetMapping("/players/insert")
    public String loadInsertPlayerPage(Model model, @ModelAttribute Player player) {
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        return "insertPlayer";
    }

    @PostMapping("/players/insert")
    public String savePlayer(@Valid Player player, Errors errors) {
        if (errors.hasErrors()) {
            return "insertPlayer";
        }
        Player savedPlayer = playerService.save(player);

        if (savedPlayer == null) {
            throw new RuntimeException("Player not saved");
        }

        return "redirect:/players";
    }

    // UPDATE
    @GetMapping("/teams/edit/{teamId}")
    public String loadEditTeamPage(@ModelAttribute Team team, Model model,
                               @PathVariable Long teamId) {
        Team teamToEdit = teamService.findById(teamId);
        model.addAttribute("team", teamToEdit);
        return "InsertTeam";
    }

    @PostMapping("/teams/edit/{teamId}")
    public String updateTeam(@Valid Team team, Errors errors,
                                 @PathVariable Long teamId) {
        team.setId(teamId);
        if (errors.hasErrors()) {
            return "InsertTeam";
        }

        Team savedTeam = teamService.save(team);

        if (savedTeam == null) {
            throw new RuntimeException("Team not saved");
        }

        return "redirect:/teams";
    }

    @GetMapping("/players/edit/{playerId}")
    public String loadEditPlayerPage(@ModelAttribute Player player, Model model,
                                   @PathVariable Long playerId) {

        Player playerToEdit = playerService.findById(playerId);
        model.addAttribute("player", playerToEdit);
        return "insertPlayer";
    }

    @PostMapping("/player/edit/{playerId}")
    public String updatePlayer(@Valid Player player, Errors errors,
                             @PathVariable Long playerId) {
        player.setId(playerId);

        if (errors.hasErrors()) {
            return "insertPlayer";
        }

        Player savedPlayer = playerService.save(player);

        if (savedPlayer == null) {
            throw new RuntimeException("Player not saved");
        }

        return "redirect:/players";
    }

}
