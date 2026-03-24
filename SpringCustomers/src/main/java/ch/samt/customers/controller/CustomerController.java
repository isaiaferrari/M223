package ch.samt.customers.controller;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.domain.Customer;
import ch.samt.customers.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/customers")
@Controller
public class CustomerController {

    //private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        //this.customerRepository = customerRepository;
        this.customerService = customerService;
    };

    /*
    private final List<Customer> customers = new ArrayList<>(
            Arrays.asList(
                    new Customer(1L, "Mario", "Rossi", 40, "Lugano", "11/29", 123),
                    new Customer(2L, "Giorgio", "Verdi", 30, "Bellinzona", "01/30", 654),
                    new Customer(3L, "Ennio", "Bianchi", 33, "Chiasso", "07/27", 890)
            ));
    */
    @GetMapping
    public String loadCustomers(Model model) {
        //model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("customers", customerService.findAll());
        return "customerList";
    }

    @GetMapping("/insert")
    public String loadInsertPage(@ModelAttribute Customer customer) {
        return "insertCustomer";
    }

    @PostMapping("/insert")
    public String saveCustomers(@Valid Customer customer, Errors errors) {
        if (errors.hasErrors()) {
            return "insertCustomer";
        }
        //customers.add(customer);
        //customerRepository.save(customer);
        Customer savedCustomer = customerService.save(customer);

        if (savedCustomer == null) {
            throw new RuntimeException("Customer not saved");
        }

        return "redirect:/customers";
    }

    @GetMapping("/{surnameToFilter}")
    public String loadInsertPage(Model model, @PathVariable String surnameToFilter) {
        //List<Customer> filteredCustomers = customers.stream()
        //        .filter(customer -> customer.getSurname().equalsIgnoreCase(surnameToFilter)).toList();
        List <Customer> filteredCustomers = customerService.findBySurnameIgnoreCase(surnameToFilter);
        model.addAttribute("customers", filteredCustomers);
        return "customerList";
    }

    @GetMapping("/customersbycity")
    public String findByCity(Model model,
                                 @RequestParam(value = "city", required = false) String cityName) {

        List <Customer> filteredCustomers = customerService.findByCityIgnoreCase(cityName);
        model.addAttribute("customers", filteredCustomers);
        return "customerList";
    }

    @GetMapping("/customersbyageafter")
    public String findByAgeAfter(Model model,
                             @RequestParam(value = "age", required = false) Integer age) {

        List <Customer> filteredCustomers = customerService.findByAgeAfter(age);
        model.addAttribute("customers", filteredCustomers);
        return "customerList";
    }

}