package ch.samt.customers.service;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.domain.Customer;
import ch.samt.customers.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findBySurnameIgnoreCase(String name) {
        return customerRepository.findBySurnameIgnoreCase(name);
    }

    public List<Customer> findByAgeAfter(Integer age){
        return customerRepository.findByAgeAfter(age);
    }
}
