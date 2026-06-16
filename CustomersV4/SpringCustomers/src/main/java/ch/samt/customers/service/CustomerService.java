package ch.samt.customers.service;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.domain.Customer;
import ch.samt.customers.domain.Reservation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(long id){
        return customerRepository.findById(id);
    }

    public List<Customer> findByAgeAfter(Integer age){
        return customerRepository.findByAgeAfter(age);
    }

    @Cacheable(value = "customerBySurname", key = "#surnameToFilter.toLowerCase()")
    public List<Customer> findBySurnameIgnoreCase(String surnameToFilter) {
        log.debug("Cache miss - querying DB for surname: {}", surnameToFilter);
        return customerRepository.findBySurnameIgnoreCase(surnameToFilter);
    }

    @CacheEvict(value = "customerBySurname", key = "#customer.surname.toLowerCase()")
    public Customer save(@Valid Customer customer) {
        log.info("Saving customer, evicting cache for surname: {}", customer.getSurname());
        try {
            return customerRepository.save(customer);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Errore al save del customer in DB: " + customer, ex);
        }
    }
}
