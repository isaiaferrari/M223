package ch.samt.customers.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;

import java.text.DateFormat;

@Data
@AllArgsConstructor
public class Customer {

    @Id
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
