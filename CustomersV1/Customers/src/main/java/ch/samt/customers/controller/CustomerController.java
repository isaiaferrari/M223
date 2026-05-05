package ch.samt.customers.controller;

import ch.samt.customers.model.Customer;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RequestMapping("/customers")
@Controller
public class CustomerController {
    private final List<Customer> customers = new ArrayList<>(
            Arrays.asList(
                    new Customer(1L, "Mario", "Rossi", 40),
                    new Customer(2L, "Giorgio", "Verdi", 30),
                    new Customer(3L, "Ennio", "Bianchi", 33)
            ));

    @GetMapping("/insert")
    public String loadInsertPage(@ModelAttribute Customer customer) {
        return "insertCustomer";
    }

    @PostMapping("/insert")
    public String saveCustomer(@ModelAttribute @Valid Customer customer, Model model, Errors errors) {
        if (errors.hasErrors()){
            return "insertCustomer";
        }
        customers.add(customer);
        model.addAttribute("customers", customers);
        return "customerList";
    }

    @GetMapping("/customers")
    public String loadCustomers(Model model,
                                @RequestParam(value = "id", required = false) Long customerId) {
        if (customerId == null) {
            model.addAttribute("customers", customers);
        } else {
            Customer customer = customers.stream()
                    .filter(c -> c.getId().equals(customerId))
                    .findFirst().orElse(null);
            model.addAttribute("customers", customer);
        }
        return "customerList";
    }


    @GetMapping("/customers/{id}")
    public String getCustomerByPathId(Model model,
                                      @PathVariable("id") Long customerId) {
        Customer customer = customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst().orElse(null);
        model.addAttribute("customers", customer);
        return "customerList";
    }
}
