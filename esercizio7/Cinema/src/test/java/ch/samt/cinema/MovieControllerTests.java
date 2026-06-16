package ch.samt.cinema;

import ch.samt.cinema.domain.Movie;
import ch.samt.cinema.service.ActorService;
import ch.samt.cinema.service.DirectorService;
import ch.samt.cinema.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc  // Abilita l'uso di MockMvc per simulare le chiamate HTTP (GET, POST, ecc.)
@SpringBootTest        // Avvia l'applicazione Spring con tutte le sue componenti
@Transactional         // Ogni test viene eseguito in una transazione: le modifiche al DB vengono annullate al termine
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ActorService actorService;

    // -------------------------------------------------------------------------
    // MOVIES
    // -------------------------------------------------------------------------

    @Test
    public void testLoadMovies() throws Exception {
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())                                           // Stato HTTP 200
                .andExpect(view().name("moviesList"))                                 // Vista corretta
                .andExpect(model().attribute("movies", movieService.findAll()))       // Dati uguali al service
                .andExpect(model().attribute("movies", hasSize(3)))                   // 3 film nel data-dev.sql
                .andExpect(model().attribute("movies",
                        hasItem(hasProperty("title", is("Inception")))));             // Inception presente
    }

    @Test
    public void testLoadInsertMoviePage() throws Exception {
        mockMvc.perform(get("/movies/insert"))
                .andExpect(status().isOk())                           // Stato HTTP 200
                .andExpect(view().name("insertMovie"))                // Vista del form di inserimento
                .andExpect(model().attributeExists("movie"))          // Oggetto movie vuoto nel model
                .andExpect(model().attributeExists("directors"))      // Lista dei registi per il select
                .andExpect(model().attributeExists("actors"));        // Lista degli attori per il select
    }

    @Test
    public void testSaveMovie_Success() throws Exception {
        // Recupera l'id reale di Nolan dal DB (non hardcodare 1!)
        Long directorId = directorService.findAll().stream()
                .filter(d -> d.getSurname().equals("Nolan"))
                .findFirst().get().getId();

        mockMvc.perform(post("/movies/insert")
                        .param("title", "The Dark Knight")
                        .param("release_year", "2008")
                        .param("director.id", String.valueOf(directorId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        // Ricarica dal DB per avere l'oggetto completo con le relazioni
        Movie saved = movieService.findAll().stream()
                .filter(m -> m.getTitle().equals("The Dark Knight"))
                .findFirst().orElse(null);

        assert saved != null;
        assert saved.getTitle().equals("The Dark Knight");
        assert saved.getRelease_year().equals(2008);
        // Ricarica il director tramite service per avere i dati completi
        assert directorService.findById(saved.getDirector().getId())
                .getSurname().equals("Nolan");
    }

    @Test
    public void testSaveMovie_WithErrors() throws Exception {
        // POST senza titolo e senza anno: la validation deve bloccare il salvataggio
        mockMvc.perform(post("/movies/insert")
                        .param("title", "")
                        .param("release_year", ""))
                .andExpect(status().isOk())                                         // Nessun redirect: torna al form
                .andExpect(view().name("insertMovie"))                              // Vista del form
                .andExpect(model().attributeHasFieldErrors("movie", "title"))       // Errore sul titolo
                .andExpect(model().attributeHasFieldErrors("movie", "release_year")); // Errore sull'anno

        // Verifica che il film NON sia stato salvato
        assert movieService.findAll().stream()
                .noneMatch(m -> m.getTitle().equals(""));
    }

    // -------------------------------------------------------------------------
    // DIRECTORS
    // -------------------------------------------------------------------------

    @Test
    public void testLoadInsertDirectorPage() throws Exception {
        mockMvc.perform(get("/director/insert"))
                .andExpect(status().isOk())                      // Stato HTTP 200
                .andExpect(view().name("insertDirector"))        // Vista corretta
                .andExpect(model().attributeExists("director")); // Oggetto director vuoto nel model
    }

    @Test
    public void testSaveDirector_Success() throws Exception {
        mockMvc.perform(post("/director/insert")
                        .param("name", "Steven")
                        .param("surname", "Spielberg"))
                .andExpect(status().is3xxRedirection())   // Redirect dopo il salvataggio
                .andExpect(redirectedUrl("/movies"));     // Redirect verso /movies

        // Verifica che Spielberg sia ora presente nel DB
        boolean found = directorService.findAll().stream()
                .anyMatch(d -> d.getSurname().equals("Spielberg"));
        assert found;
    }

    @Test
    public void testSaveDirector_WithErrors() throws Exception {
        // POST con campi vuoti: la validation (@NotBlank) deve bloccare il salvataggio
        mockMvc.perform(post("/director/insert")
                        .param("name", "")
                        .param("surname", ""))
                .andExpect(status().isOk())                                             // Nessun redirect
                .andExpect(view().name("insertDirector"))                               // Torna al form
                .andExpect(model().attributeHasFieldErrors("director", "name"))         // Errore sul nome
                .andExpect(model().attributeHasFieldErrors("director", "surname"));     // Errore sul cognome

        // Verifica che nessun director con nome vuoto sia stato inserito
        assert directorService.findAll().stream()
                .noneMatch(d -> d.getName().isEmpty());
    }

    // -------------------------------------------------------------------------
    // ACTORS
    // -------------------------------------------------------------------------

    @Test
    public void testLoadInsertActorPage() throws Exception {
        mockMvc.perform(get("/actor/insert"))
                .andExpect(status().isOk())                    // Stato HTTP 200
                .andExpect(view().name("insertActor"))         // Vista corretta
                .andExpect(model().attributeExists("actor"));  // Oggetto actor vuoto nel model
    }

    @Test
    public void testSaveActor_Success() throws Exception {
        mockMvc.perform(post("/actor/insert")
                        .param("name", "Keanu")
                        .param("surname", "Reeves"))
                .andExpect(status().is3xxRedirection())   // Redirect dopo il salvataggio
                .andExpect(redirectedUrl("/movies"));     // Redirect verso /movies

        // Verifica che Keanu Reeves sia ora presente nel DB
        boolean found = actorService.findAll().stream()
                .anyMatch(a -> a.getSurname().equals("Reeves"));
        assert found;
    }

    @Test
    public void testSaveActor_WithErrors() throws Exception {
        // POST con un solo carattere: viola @Size(min=2)
        mockMvc.perform(post("/actor/insert")
                        .param("name", "X")
                        .param("surname", "Y"))
                .andExpect(status().isOk())                                         // Nessun redirect
                .andExpect(view().name("insertActor"))                              // Torna al form
                .andExpect(model().attributeHasFieldErrors("actor", "name"))        // Errore sul nome
                .andExpect(model().attributeHasFieldErrors("actor", "surname"));    // Errore sul cognome

        // Verifica che l'attore NON sia stato inserito
        assert actorService.findAll().stream()
                .noneMatch(a -> a.getName().equals("X"));
    }

    @Test
    public void testLoadActors_ContainExpectedData() throws Exception {
        // Verifica che gli attori del data-dev.sql siano presenti
        assert actorService.findAll().stream()
                .anyMatch(a -> a.getSurname().equals("DiCaprio"));
        assert actorService.findAll().stream()
                .anyMatch(a -> a.getSurname().equals("Jackson"));
        assert actorService.findAll().size() == 7; // 7 attori nel data-dev.sql
    }

    @Test
    public void testMovies_ContainExpectedRelations() throws Exception {
        // Verifica che Inception sia associato a Nolan e abbia almeno un attore
        Movie inception = movieService.findAll().stream()
                .filter(m -> m.getTitle().equals("Inception"))
                .findFirst().orElse(null);

        assert inception != null;
        assert inception.getDirector().getSurname().equals("Nolan");
        assert inception.getActors() != null;
        assert inception.getActors().stream()
                .anyMatch(a -> a.getSurname().equals("DiCaprio"));
    }

}