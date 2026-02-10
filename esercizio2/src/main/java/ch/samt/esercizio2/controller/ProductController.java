package ch.samt.esercizio2.controller;


import ch.samt.esercizio2.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {
    private final static List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product(1L, "Mirtilli", 5.95, LocalDate.now().plusDays(20), "Mirtilli di Gian Alberto"),
                    new Product(2L, "Fragole", 6.50, LocalDate.now().plusDays(25), "Fragole di Skibidi Tiolet"),
                    new Product(3L, "Lamponi", 10.00, LocalDate.now().plusDays(10), "Lamponi di Franca Alfa Sigma"),
                    new Product(4L, "Pere", 4.80, LocalDate.now().plusDays(30), "Pere di Maria Alfonso Pascal")
            ));

    @GetMapping("/products")
    public String loadProductList(Model model) {
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/newproduct")
    public String loadInsertProductPage(@ModelAttribute Product product) {
        return "insertProduct";
    }

    @PostMapping("/newproduct")
    public String saveProduct(@ModelAttribute Product product, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "insertProduct";
        }
        products.add(product);
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/products/show/{id}")
    public String getproductByPathId(Model model,
                                      @PathVariable("id") Long productId) {
        Product product = products.stream()
                .filter(c -> c.getId().equals(productId))
                .findFirst().orElse(null);
        model.addAttribute("products", products);
        return "productList";
    }

}
