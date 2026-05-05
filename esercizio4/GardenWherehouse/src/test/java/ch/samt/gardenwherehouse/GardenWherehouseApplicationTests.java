package ch.samt.gardenwherehouse;

import ch.samt.gardenwherehouse.data.ItemRepository;
import ch.samt.gardenwherehouse.domain.Item;
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
@SpringBootTest        // Avvia l'applicazione Spring con tutte le sue componenti
@Transactional         // Ogni test viene eseguito in una transazione che viene annullata al termine, lasciando il DB pulito
class GardenWherehouseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;


    // -------------------------------------------------------
    // GET /items  →  elenco di tutti gli item
    // -------------------------------------------------------
    @Test
    public void testLoadItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())                                         // Verifica stato HTTP 200
                .andExpect(view().name("itemList"))                                 // Verifica che la vista sia "itemList"
                .andExpect(model().attributeExists("items"))                        // Verifica che il modello contenga "items"
                .andExpect(model().attribute("items", itemRepository.findAll()))    // Verifica che gli item coincidano con quelli del DB
                .andExpect(model().attribute("items", hasSize(3)));                 // Verifica che ci siano 3 item (quelli dello script di popolamento)
    }


    // -------------------------------------------------------
    // GET /items/{code}  →  dettaglio di un singolo item
    // -------------------------------------------------------
    @Test
    public void testLoadItemByCode_ExistingCode() throws Exception {
        // Recupera il codice del primo item presente nel DB (inserito dallo script di popolamento)
        String existingCode = itemRepository.findAll().get(0).getCode();

        mockMvc.perform(get("/items/" + existingCode))
                .andExpect(status().isOk())                                                             // Verifica stato HTTP 200
                .andExpect(view().name("itemList"))                                                     // Verifica che la vista sia "itemList"
                .andExpect(model().attribute("items", hasProperty("code", is(existingCode))));          // Verifica che l'item restituito abbia il codice corretto
    }

    @Test
    public void testLoadItemByCode_NonExistingCode() throws Exception {
        mockMvc.perform(get("/items/zzz-99"))
                .andExpect(status().isOk())                // Verifica stato HTTP 200 (il controller non lancia eccezione)
                .andExpect(view().name("itemList"))         // Verifica che la vista sia "itemList"
                .andExpect(model().attribute("items", nullValue()));  // L'item è null perché il codice non esiste
    }


    // -------------------------------------------------------
    // GET /items/sell?code=<code>  →  vende 1 pezzo
    // -------------------------------------------------------
    @Test
    public void testSellItem_ExistingCode() throws Exception {
        // Recupera un item con almeno 1 pezzo disponibile
        String existingCode = itemRepository.findAll().get(0).getCode();
        int countBefore = itemRepository.findByCode(existingCode).getItemCount();

        mockMvc.perform(get("/items/sell").param("code", existingCode))
                .andExpect(status().is3xxRedirection())                         // Verifica che ci sia una redirezione
                .andExpect(redirectedUrl("/items/" + existingCode));            // Verifica la redirezione a /items/<code>

        // Verifica che il contatore sia diminuito di 1
        int countAfter = itemRepository.findByCode(existingCode).getItemCount();
        assert countAfter == countBefore - 1;
    }

    @Test
    public void testSellItem_ItemOutOfStock() throws Exception {
        // Porta l'item a 0 pezzi manualmente
        Item item = itemRepository.findAll().get(0);
        item.setItemCount(0);
        itemRepository.save(item);

        mockMvc.perform(get("/items/sell").param("code", item.getCode()))
                .andExpect(status().is3xxRedirection())                     // Verifica che ci sia comunque una redirezione
                .andExpect(redirectedUrl("/items/" + item.getCode()));      // Verifica la redirezione a /items/<code>

        // Verifica che il contatore sia rimasto a 0 (non va in negativo)
        int countAfter = itemRepository.findByCode(item.getCode()).getItemCount();
        assert countAfter == 0;
    }


    // -------------------------------------------------------
    // GET /items/add?code=<code>&number=<n>  →  aggiunge n pezzi
    // -------------------------------------------------------
    @Test
    public void testAddItem_ExistingCode() throws Exception {
        String existingCode = itemRepository.findAll().get(0).getCode();
        int countBefore = itemRepository.findByCode(existingCode).getItemCount();

        mockMvc.perform(get("/items/add")
                        .param("code", existingCode)
                        .param("number", "5"))
                .andExpect(status().is3xxRedirection())                     // Verifica che ci sia una redirezione
                .andExpect(redirectedUrl("/items/" + existingCode));        // Verifica la redirezione a /items/<code>

        // Verifica che il contatore sia aumentato di 5
        int countAfter = itemRepository.findByCode(existingCode).getItemCount();
        assert countAfter == countBefore + 5;
    }

    @Test
    public void testAddItem_NonExistingCode() throws Exception {
        mockMvc.perform(get("/items/add")
                        .param("code", "zzz-99")
                        .param("number", "3"))
                .andExpect(status().is3xxRedirection())                 // Verifica che ci sia comunque una redirezione
                .andExpect(redirectedUrl("/items/zzz-99"));            // Verifica la redirezione a /items/zzz-99

        // Verifica che nel DB non sia stato creato nessun item con codice "zzz-99"
        assert itemRepository.findByCode("zzz-99") == null;
    }


    // -------------------------------------------------------
    // GET /items/insert  →  carica la pagina di inserimento
    // -------------------------------------------------------
    @Test
    public void testLoadInsertPage() throws Exception {
        mockMvc.perform(get("/items/insert"))
                .andExpect(status().isOk())                         // Verifica stato HTTP 200
                .andExpect(view().name("insertItem"))               // Verifica che la vista sia "insertItem"
                .andExpect(model().attributeExists("item"));        // Verifica che il modello contenga un attributo "item"
    }


    // -------------------------------------------------------
    // POST /items/insert  →  inserisce un nuovo item
    // -------------------------------------------------------
    @Test
    public void testInsertItem_ValidData() throws Exception {
        mockMvc.perform(post("/items/insert")
                        .param("code", "xyz-99")
                        .param("type", "Attrezzo")
                        .param("name", "Annaffiatoio")
                        .param("price", "14.90")
                        .param("itemCount", "7"))
                .andExpect(status().is3xxRedirection())         // Verifica che ci sia una redirezione
                .andExpect(redirectedUrl("/items"));            // Verifica la redirezione a /items

        // Verifica che l'item sia stato effettivamente salvato nel DB
        Item savedItem = itemRepository.findByCode("xyz-99");
        assert savedItem != null;
        assert savedItem.getName().equals("Annaffiatoio");
        assert savedItem.getType().equals("Attrezzo");
        assert savedItem.getPrice() == 14.90;
        assert savedItem.getItemCount() == 7;
    }

    @Test
    public void testInsertItem_InvalidCode() throws Exception {
        // Il codice "INVALIDO" non rispetta il pattern ^[a-z]{3}-\d{2}$
        mockMvc.perform(post("/items/insert")
                        .param("code", "INVALIDO")
                        .param("type", "Attrezzo")
                        .param("name", "Annaffiatoio")
                        .param("price", "14.90")
                        .param("itemCount", "7"))
                .andExpect(status().isOk())                                             // Nessuna redirezione: il form viene ricaricato
                .andExpect(view().name("itemList"))                                     // Verifica che la vista sia "itemList"
                .andExpect(model().attributeHasFieldErrors("item", "code"));            // Verifica l'errore sul campo "code"

        // Verifica che l'item NON sia stato salvato nel DB
        assert itemRepository.findByCode("INVALIDO") == null;
    }

    @Test
    public void testInsertItem_BlankName() throws Exception {
        // Il campo "name" è obbligatorio (@NotBlank)
        mockMvc.perform(post("/items/insert")
                        .param("code", "xyz-99")
                        .param("type", "Attrezzo")
                        .param("name", "")
                        .param("price", "14.90")
                        .param("itemCount", "7"))
                .andExpect(status().isOk())                                             // Nessuna redirezione: il form viene ricaricato
                .andExpect(view().name("itemList"))                                     // Verifica che la vista sia "itemList"
                .andExpect(model().attributeHasFieldErrors("item", "name"));            // Verifica l'errore sul campo "name"

        // Verifica che l'item NON sia stato salvato nel DB
        assert itemRepository.findByCode("xyz-99") == null;
    }

    @Test
    public void testInsertItem_DuplicateCode() throws Exception {
        // Recupera un codice già esistente nel DB
        String existingCode = itemRepository.findAll().get(0).getCode();

        // Il controller lancia RuntimeException in caso di codice duplicato → Spring risponde con 500
        mockMvc.perform(post("/items/insert")
                        .param("code", existingCode)
                        .param("type", "Attrezzo")
                        .param("name", "Duplicato")
                        .param("price", "5.00")
                        .param("itemCount", "1"))
                .andExpect(status().is5xxServerError());    // Verifica che venga lanciata un'eccezione (RuntimeException)
    }

}
