
import ch.samt.esercizio2.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("productPage"))
                .andExpect(content().string(containsString("<th>Name</th>")))
                .andExpect(content().string(containsString("Cappotto")));
    }

    @Test
    public void testNewProductsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/newproduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("newProductPage"))
                .andExpect(content().string(containsString("Insert new product")));
    }

    @Test
    public void testNewProductsPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/newproduct")
                        .param("id", "4")
                        .param("name", "Pantalone")
                        .param("price", "123"))
                .andExpect(status().is3xxRedirection());
    }
}
