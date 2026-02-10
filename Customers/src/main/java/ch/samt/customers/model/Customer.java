package ch.samt.customers.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Customer {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
}