package ch.samt.customers.controller;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.domain.Customer;
import ch.samt.customers.domain.MealGroup;
import ch.samt.customers.service.CustomerService;
import ch.samt.customers.service.MealGroupService;
import ch.samt.customers.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customers")
@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final MealGroupService mealGroupService;

    @Autowired
    public CustomerController(CustomerService customerService, ReservationService reservationService, MealGroupService mealGroupService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
        this.mealGroupService = mealGroupService;
    };


    @GetMapping
    public String loadCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customerList";
    }

    @GetMapping("/reservations")
    public String loadReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservationList";
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
        Customer savedCustomer = customerService.save(customer);

        if (savedCustomer == null) {
            throw new RuntimeException("Customer not saved");
        }

        return "redirect:/customers";
    }

    @GetMapping("/edit/{customerId}")
    public String loadEditPage(@ModelAttribute Customer customer, Model model,
                               @PathVariable Long customerId) {
        Customer customerToEdit = customerService.findById(customerId);
        model.addAttribute("customer", customerToEdit);
        return "insertCustomer";
    }

    @PostMapping("/edit/{customerId}")
    public String updateCustomer(@Valid Customer customer, Errors errors,
                          @PathVariable Long customerId){
        customer.setId(customerId);
        if (errors.hasErrors()) {
            return "insertCustomer";
        }

        Customer savedCustomer = customerService.save(customer);

        if (savedCustomer == null) {
            throw new RuntimeException("Customer not saved");
        }

        return "redirect:/customers";
    }

    @GetMapping("/{surnameToFilter}")
    public String loadCustomerBySurname(Model model,
                                        @PathVariable String surnameToFilter) {
        List <Customer> filteredCustomers = customerService.findBySurnameIgnoreCase(surnameToFilter);
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

    @GetMapping("/mealgroups")
    public String mealGroups(Model model) {
        model.addAttribute("mealGroups", mealGroupService.findAll());
        model.addAttribute("customers", customerService.findAll());

        return "mealGroupList";
    }


    @PostMapping("/addcustomer-to-mealgroup")
    public String addCustomerToMealGroup(@RequestParam Long mealGroupId,
                                         @RequestParam Long customerId) {
        MealGroup mealGroup = mealGroupService.findById(mealGroupId);
        Customer customer = customerService.findById(customerId);

        if (!customer.getMealGroups().contains(mealGroup)) {
            customer.getMealGroups().add(mealGroup);
            customerService.save(customer);
        }
        return "redirect:/customers/mealgroups";
    }

}