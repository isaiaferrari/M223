package ch.samt.esercizio1.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class User {
    private Long id;
    private String name;
    private String surname;
}
