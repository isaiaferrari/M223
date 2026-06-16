package ch.samt.videogames.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String nationality;

    @NotBlank
    @Size(min = 8, max = 16, message = "Lunghezza tra 8 e 16 caratteri")
    private String contractExpirationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameprofile_id")
    @Valid
    private GameProfile gameProfile;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
