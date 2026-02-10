package ch.samt.esercizio2.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Product {
    @NotNull
    private Long id;

    @NotBlank(message = "il campo è obbligatorio")
    @Size(min = 2, max = 100, message = "deve essere di almeno 2 caratteri e sotto i 100")
    private String name;

    @Positive(message = "il prezzo deve essere positivo")
    private double price;

    @FutureOrPresent(message = "il prodotto non può essere scaduto")
    private LocalDate expirationDate;


    private String description;
}
