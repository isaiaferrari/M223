package ch.samt.cinema.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_seq")
    @SequenceGenerator(name = "actor_seq", sequenceName = "actor_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20, message = "Lunghezza tra 2 e 10 caratteri")
    private String name;

    @NotBlank
    @Size(min = 2, max = 20, message = "Lunghezza tra 2 e 10 caratteri")
    private String surname;

    @ToString.Exclude
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
}
