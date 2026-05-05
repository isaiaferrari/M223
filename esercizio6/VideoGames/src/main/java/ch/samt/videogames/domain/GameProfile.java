package ch.samt.videogames.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class GameProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameprofile_seq")
    @SequenceGenerator(name = "gameprofile_seq", sequenceName = "gameprofile_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private Integer level;

    @NotBlank
    private String winRate;

    @NotBlank
    private String kdaRatio;

    @NotBlank
    private String tier;

}
