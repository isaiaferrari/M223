package ch.samt.cinema.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "movie_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50, message = "Lunghezza tra 1 e 50 caratteri")
    private String title;

    @NotNull
    @Min(1900)
    @Max(2050)
    private Integer release_year;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany
    @JoinTable( name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn( name = "actor_id"))
    @ToString.Exclude
    private List<Actor> actors;

}
