package ch.samt.customers.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotBlank
    @Size(min = 1, max = 3, message = "Lunghezza tra 3 e 30 caratteri")
    private String room;

    @NotBlank
    @Size(min = 1, max = 3, message = "Numeri da 1 a 999")
    private String num;

    @NotBlank
    @Size(min = 8, max = 16, message = "Lunghezza tra 8 e 16 caratteri")
    private String checkin;

    @NotBlank
    @Size(min = 8, max = 16, message = "Lunghezza tra 8 e 16 caratteri")
    private String checkout;
}
