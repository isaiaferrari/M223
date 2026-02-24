package ch.samt.esercizio2.controller;


import ch.samt.esercizio2.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class ProductController {

    private final static List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product(1L, "Mirtilli", 5.95, LocalDate.now().plusDays(20), "Mirtilli di Gian Alberto"),
                    new Product(2L, "Fragole", 6.50, LocalDate.now().plusDays(25), "Fragole di Skibidi Tiolet"),
                    new Product(3L, "Lamponi", 10.00, LocalDate.now().plusDays(10), "Lamponi di Franca Alfa Sigma"),
                    new Product(4L, "Pere", 4.80, LocalDate.now().plusDays(30), "Pere di Maria Alfonso Pascal")
            ));

    /*
    private final static Map<Long, Product> products = new HashMap<>();
            static {
                products.put(1L, new Product(1L, "Mirtilli", 5.95, LocalDate.now().plusDays(20), "Mirtilli di Gian Alberto"));
                products.put(2L, new Product(2L, "Fragole", 6.50, LocalDate.now().plusDays(25), "Fragole di Skibidi Tiolet"));
                products.put(3L, new Product(3L, "Lamponi", 10.00, LocalDate.now().plusDays(10), "Lamponi di Franca Alfa Sigma"));
                products.put(4L, new Product(4L, "Pere", 4.80, LocalDate.now().plusDays(30), "Pere di Maria Alfonso Pascal"));
            }
    */

    @GetMapping("/products")
    public String loadProductList(Model model,
                                  @RequestParam(value = "name", required = false) String productName,
                                  @RequestParam(value = "pricelessthan", required = false) Double price){

        if (productName == null) {
            model.addAttribute("products", products);
        } else {
            List<Product> filteredProducts = products.stream()
                    .filter(c -> c.getName().equals(productName))
                    .toList();
            model.addAttribute("products", filteredProducts);
        }

        if (price == null) {
            model.addAttribute("products", products);
        } else {
            List<Product> filteredProducts = products.stream()
                    .filter(c -> c.getPrice() < price)
                    .toList();
            model.addAttribute("products", filteredProducts);
        }

        return "productList";
    }

    @GetMapping("/newproduct")
    public String loadInsertProductPage(@ModelAttribute Product product) {
        return "insertProduct";
    }

    @PostMapping("/newproduct")
    public String saveProduct(@ModelAttribute @Valid Product product, Model model, Errors errors) {
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
