package ch.samt.customers.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 30, message = "Lunghezza tra 3 e 30 caratteri")
    private String street;

    @NotBlank
    @Size(min = 1, max = 3, message = "Numeri da 1 a 5 cifre")
    private String num;

    @NotBlank
    @Size(min = 4, max = 7, message = "Lunghezza tra 4 e 7 caratteri")
    private String zip;

    @NotBlank
    @Size(min = 3, max = 30, message = "Lunghezza tra 3 e 30 caratteri")
    private String city;

    @NotBlank
    @Size(min = 3, max = 30, message = "Lunghezza tra 3 e 30 caratteri")
    private String nation;
}
