package ch.samt.blog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_seq")
    @SequenceGenerator(name = "blog_seq", sequenceName = "blog_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "Il titolo deve essere maggiore di 3 e minore di 100 caratteri")
    private String title;

    @NotNull
    private java.time.LocalDate publishedDate;

    @NotBlank
    @Size(min = 3, max = 50, message = "La categoria deve essere maggiore di 3 e minore di 50 caratteri")
    private String category;

    @NotBlank
    @Size(min = 2, max = 50, message = "L'autore deve essere maggiore di 2 e minore di 50 caratteri")
    private String author;

    @NotNull
    @Min(value = 0, message = "I like non possono essere negativi")
    private Integer likes;

    @NotBlank
    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;
}