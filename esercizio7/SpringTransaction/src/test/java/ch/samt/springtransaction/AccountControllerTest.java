package ch.samt.springtransaction;

import ch.samt.springtransaction.data.AccountRepository;
import ch.samt.springtransaction.domain.Account;
import ch.samt.springtransaction.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountControllerTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testTransfer() {
        accountRepository.save(new Account("account1", 1000));
        accountRepository.save(new Account("account2", 0));

        try{
            accountService.transfer("account1", "account2", 600);
        } catch (Exception e){
            System.out.println("Si è verificato un errore");

            assertEquals(1000, accountRepository.findById("account1").orElseThrow().getBalance());
            assertEquals(0, accountRepository.findById("account2").orElseThrow().getBalance());
            return;
        }

        assertEquals(400, accountRepository.findById("account1").orElseThrow().getBalance());
        assertEquals(600, accountRepository.findById("account2").orElseThrow().getBalance());
    }
}
