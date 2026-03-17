package ch.samt.customers.data;

import ch.samt.customers.domain.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository
        extends CrudRepository<Customer, Long> {

    @Query("SELECT * FROM customer WHERE LOWER(surname) = LOWER(:surname)")
    List<Customer> findBySurname(String surname);

    @Query("SELECT * FROM customer WHERE LOWER(city) = LOWER(:city)")
    List<Customer> findByCity(String city);
}
