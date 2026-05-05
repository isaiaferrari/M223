package ch.samt.customers.data;

import ch.samt.customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

   Customer findById(long id);
   List<Customer> findBySurnameIgnoreCase(String surname);
   List<Customer> findByAgeAfter(Integer age);
}
