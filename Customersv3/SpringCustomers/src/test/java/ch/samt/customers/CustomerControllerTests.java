package ch.samt.customers;

import ch.samt.customers.service.CustomerService;
import ch.samt.customers.service.ReservationService;
import ch.samt.customers.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc  // Abilita l'uso di MockMvc che ci permette di simulare le chiamate HTTP (GET, POST, ecc..)
@SpringBootTest  // Avvia l'applicazione Spring con tutte le sue componenti
@Transactional  // Non del tutto necessario qua, ma assicura che ogni test sia eseguito in modo transazionale (tutto o niente, modifiche incomplete vengono annullate)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testLoadCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())  // Verifica che lo stato sia OK (200)
                .andExpect(view().name("customerList"))  // Verifica che la vista sia "customerList"
                .andExpect(model().attribute("customers", customerService.findAll()))  // Verifica che i clienti contenuti in "customers" siano esattamente quelli della chiamata "customerService.findAll()"
                .andExpect(model().attribute("customers", hasSize(3)))  // Verifica il numero di clienti
                .andExpect(model().attribute("customers", hasItem(hasProperty("name", is("Mario")))));  // Verifica che ci sia un cliente di nome "Mario"
    }

    @Test
    public void testLoadReservation() throws Exception {
        mockMvc.perform(get("/customers/reservations"))
                .andExpect(status().isOk())  // Verifica che lo stato sia OK (200)
                .andExpect(view().name("reservationList"))  // Verifica che la vista sia "customerList"
                .andExpect(model().attribute("reservations", reservationService.findAll()))  // Verifica che i clienti contenuti in "customers" siano esattamente quelli della chiamata "customerService.findAll()"
                .andExpect(model().attribute("reservations", hasSize(3)))  // Verifica il numero di riservazioni
                .andExpect(model().attribute("reservations", hasItem(hasProperty("room", is("437")))));  // Verifica che ci sia un cliente di nome "Mario"
    }

    @Test
    public void testLoadInsertPage() throws Exception {
        mockMvc.perform(get("/customers/insert"))
                .andExpect(status().isOk())  // Verifica che lo stato sia OK (200)
                .andExpect(view().name("insertCustomer"))  // Verifica che la vista sia "insertCustomer"
                .andExpect(model().attributeExists("customer"));  // Verifica che il modello contenga un attributo "customer"
    }


    @Test
    public void testSaveCustomer_Success() throws Exception {
        // Testa la POST con dati validi
        mockMvc.perform(post("/customers/insert")
                        .param("name", "Gino")
                        .param("surname", "Bartali")
                        .param("age", "70")
                        .param("address.street", "Piazza Riforma")
                        .param("address.num", "34")
                        .param("address.zip", "6950")
                        .param("address.city", "Lugano")
                        .param("address.nation", "Svizzera"))
                .andExpect(status().is3xxRedirection())  // Verifica che ci sia una redirezione come implementato nel metodo => return "redirect:/customers"
                .andExpect(redirectedUrl("/customers"));  // Verifica la redirezione a /customers

        // Verifica che Bartali sia ora presente in DB
        Customer savedCustomer = customerService.findBySurnameIgnoreCase("Bartali").get(0);
        assert savedCustomer != null;
        assert savedCustomer.getName().equals("Gino");
        assert savedCustomer.getSurname().equals("Bartali");
        assert savedCustomer.getAge().equals(70);
        assert savedCustomer.getAddress().getStreet().equals("Piazza Riforma");
        assert savedCustomer.getAddress().getNum().equals("34");
        assert savedCustomer.getAddress().getZip().equals("6950");
        assert savedCustomer.getAddress().getCity().equals("Lugano");
        assert savedCustomer.getAddress().getNation().equals("Svizzera");
    }

    @Test
    public void testSaveCustomer_WithErrors() throws Exception {
        // Testa il funzionamento della validation (manca l'età del cliente e quindi il tentativo di save non andrà a buon fine)
        mockMvc.perform(post("/customers/insert")
                        .param("name", "Fausto")
                        .param("surname", "Coppi"))
                .andExpect(status().isOk())  // Lo stato è OK, ma non c'è redirezione
                .andExpect(view().name("insertCustomer"))  // La vista restituita è di nuovo il form
                .andExpect(model().attributeHasFieldErrors("customer", "age"));  // Verifica l'errore sul campo "age"

        // Verifica che Fausto Coppi NON sia stato inserito in DB
        assert customerService.findBySurnameIgnoreCase("Coppi").isEmpty();
        //  Provare a commentare la validation sul campo "age" e verificare che questo test fallisce
    }

    @Test
    public void testLoadCustomersBySurname() throws Exception {
        mockMvc.perform(get("/customers/Rossi"))
                .andExpect(status().isOk())  // Verifica che lo stato sia OK (200)
                .andExpect(view().name("customerList"))  // Verifica che la vista sia "customerList"
                .andExpect(model().attributeExists("customers"))  // Verifica che il modello contenga "customers"
                .andExpect(model().attribute("customers", customerService.findBySurnameIgnoreCase("Rossi")));  // Verifica che "customer" contenga il risultato della query
    }

    @Test
    public void testEditCustomer_Success() throws Exception {
        mockMvc.perform(post("/customers/edit/1")
                .param("name", "Mario")
                .param("surname", "Rossi")
                .param("age", "24")
                .param("address.street", "Piazza Guisan")
                .param("address.num", "10") //cambio numero
                .param("address.zip", "6512")
                .param("address.city", "Giubiasco")
                .param("address.nation", "Svizzera"))

                .andExpect(status().is3xxRedirection())  // Verifica che ci sia una redirezione come implementato nel metodo => return "redirect:/customers"
                .andExpect(redirectedUrl("/customers"));

        Customer savedCustomer = customerService.findBySurnameIgnoreCase("Rossi").get(0);
        assert savedCustomer != null;
        assert savedCustomer.getName().equals("Mario");
        assert savedCustomer.getSurname().equals("Rossi");
        assert savedCustomer.getAge().equals(24);
        assert savedCustomer.getAddress().getStreet().equals("Piazza Guisan");
        assert savedCustomer.getAddress().getNum().equals("10");
        assert savedCustomer.getAddress().getZip().equals("6512");
        assert savedCustomer.getAddress().getCity().equals("Giubiasco");
        assert savedCustomer.getAddress().getNation().equals("Svizzera");
    }

    @Test
    public void testEditCustomer_WithErrors() throws Exception {
        mockMvc.perform(post("/customers/edit/1")
                        .param("name", "Fausto")
                        .param("surname", "Coppi"))
                .andExpect(status().isOk())  // Lo stato è OK, ma non c'è redirezione
                .andExpect(view().name("insertCustomer"))  // La vista restituita è di nuovo il form
                .andExpect(model().attributeHasFieldErrors("customer", "age"));  // Verifica l'errore sul campo "age"

        // Verifica che Fausto Coppi NON sia stato inserito in DB
        assert customerService.findBySurnameIgnoreCase("Coppi").isEmpty();
        //  Provare a commentare la validation sul campo "age" e verificare che questo test fallisce
    }

}