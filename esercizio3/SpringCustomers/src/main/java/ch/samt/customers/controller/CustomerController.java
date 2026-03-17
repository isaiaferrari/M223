package ch.samt.customers.controller;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.domain.Customer;
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

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        model.addAttribute("customers", customerRepository.findAll());
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
        customerRepository.save(customer);

        return "redirect:/customers";
    }

    @GetMapping("/{surnameToFilter}")
    public String loadInsertPage(Model model, @PathVariable String surnameToFilter) {
        //List<Customer> filteredCustomers = customers.stream()
        //        .filter(customer -> customer.getSurname().equalsIgnoreCase(surnameToFilter)).toList();
        List <Customer> filteredCustomers = customerRepository.findBySurname(surnameToFilter);
        model.addAttribute("customers", filteredCustomers);
        return "customerList";
    }

    @GetMapping("/customersbycity")
    public String findByCity(Model model,
                                 @RequestParam(value = "city", required = false) String cityName) {

        List <Customer> filteredCustomers = customerRepository.findByCity(cityName);
        model.addAttribute("customers", filteredCustomers);
        return "customerList";
    }

}