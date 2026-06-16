package ch.samt.customers.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class MealGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mealgroup_seq")
    @SequenceGenerator(name = "mealgroup_seq", sequenceName = "mealgroup_seq", allocationSize = 1)
    private Long id;

    @ManyToMany(mappedBy = "mealGroups")
    private List<Customer> customers;

    @NotBlank
    @Size(min = 5, max = 30, message = "Lunghezza accettata fra 5 e 30 caratteri")
    private String name;

    @NotBlank
    @Size(min = 5, max = 100, message = "Lunghezza accettata fra 5 e 100 caratteri")
    private String description;

}
