package ch.samt.customers.domain;

import jakarta.persistence.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.ToString;

import java.util.List;


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @Valid
    private Address address;

    @ToString.Exclude
    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable( name = "customer_mealgroup",
                joinColumns = @JoinColumn(name = "customer_id"),
                inverseJoinColumns = @JoinColumn( name = "mealgroup_id"))
    @ToString.Exclude
    private List<MealGroup> mealGroups;

}
