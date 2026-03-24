package ch.samt.gardenwherehouse.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-z]{3}-\\d{2}$", message = "Il codice deve essere nel formato abc-123")
    private String code;

    @NotBlank
    @Size(min = 2, max = 20)
    private String type;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    @Max(1000)
    private int itemCount;
}
