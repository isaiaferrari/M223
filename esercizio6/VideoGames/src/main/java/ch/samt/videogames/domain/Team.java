package ch.samt.videogames.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name = "team_seq", sequenceName = "team_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String headquarters;

    @NotBlank
    private String mainSponsor;

    @NotNull
    private double annualBudget;

    private Boolean deleted;

    @ToString.Exclude
    @OneToMany
    private List<Player> players;
}
