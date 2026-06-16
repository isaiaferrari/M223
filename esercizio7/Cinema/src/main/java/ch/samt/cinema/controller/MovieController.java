package ch.samt.cinema.controller;

import ch.samt.cinema.data.ActorRepository;
import ch.samt.cinema.data.DirectorRepository;
import ch.samt.cinema.data.MovieRepository;
import ch.samt.cinema.domain.Actor;
import ch.samt.cinema.domain.Director;
import ch.samt.cinema.domain.Movie;
import ch.samt.cinema.service.ActorService;
import ch.samt.cinema.service.DirectorService;
import ch.samt.cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {
        private final MovieService movieService;
        private final ActorService actorService;
        private final DirectorService directorService;


    @Autowired
    public MovieController(MovieService movieService, DirectorService directorService, ActorService actorService) {
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
    }

    @GetMapping("/movies")
    public String loadMovies(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "moviesList";
    }

    @GetMapping("/movies/insert")
    public String loadInsertMoviePage(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("directors", directorService.findAll());
        model.addAttribute("actors", actorService.findAll());
        return "insertMovie";
    }

    @PostMapping("/movies/insert")
    public String saveMovie(@Valid Movie movie, Errors errors) {
        if (errors.hasErrors()) {
            return "insertMovie";
        }
        Movie savedMovie = movieService.save(movie);

        if (savedMovie == null) {
            throw new RuntimeException("Movie not saved");
        }

        return "redirect:/movies";
    }

    @GetMapping("/director/insert")
    public String loadInsertDirectorPage(@ModelAttribute Director director) {
        return "insertDirector";
    }

    @PostMapping("/director/insert")
    public String saveDirector(@Valid Director director, Errors errors) {
        if (errors.hasErrors()) {
            return "insertDirector";
        }
        Director savedDirector = directorService.save(director);

        if (savedDirector == null) {
            throw new RuntimeException("Director not saved");
        }

        return "redirect:/movies";
    }

    @GetMapping("/actor/insert")
    public String loadInsertActorPage(@ModelAttribute Actor actor) {
        return "insertActor";
    }

    @PostMapping("/actor/insert")
    public String saveActor(@Valid Actor actor, Errors errors) {
        if (errors.hasErrors()) {
            return "insertActor";
        }
        Actor savedActor = actorService.save(actor);

        if (savedActor == null) {
            throw new RuntimeException("Actor not saved");
        }

        return "redirect:/movies";
    }

}
