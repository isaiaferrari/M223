package ch.samt.customers.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;


@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20, message = "Lunghezza tra 2 e 10 caratteri")
    private String name;

    @NotBlank
    @Size(min = 2, max = 20, message = "Lunghezza tra 2 e 10 caratteri")
    private String surname;

    @NotNull
    @Min(18)
    @Max(99)
    private Integer age;

    @NotBlank
    @Size(min = 3, max = 20)
    private String city;

    @NotBlank
    @CreditCardNumber
    private String ccNumber;

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "La scadenza deve essere nel formato MM/YY")
    private String ccExpiration;


    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Il CVV deve essere composto da 3 cifre")
    private Integer ccCVV;

}
