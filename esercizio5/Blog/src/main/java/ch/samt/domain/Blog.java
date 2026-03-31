package ch.samt.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String title;

    @Pattern(regexp = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$", message = "La data deve essere nel formato gg/mm/yyyy")
    private String date;

    @NotBlank
    @Size(min = 2, max = 50)
    private String category;

    @NotBlank
    @Size(min = 2, max = 50)
    private String author;

    @NotNull
    @Min(0)
    private int likes;

    @NotBlank
    @Lob
    private String content;
}
