package ch.samt.springtransaction.service;

import ch.samt.springtransaction.data.AccountRepository;
import ch.samt.springtransaction.domain.Account;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(String account1Id, String account2Id, int amount) {
        Account account1 = accountRepository.findById(account1Id).orElseThrow();
        Account account2 = accountRepository.findById(account2Id).orElseThrow();

        account1.setBalance(account1.getBalance() - amount);
        accountRepository.save(account1);

//        if(true){
//            throw new RuntimeException("Any Error");
//        }

        account2.setBalance(account2.getBalance() + amount);
        accountRepository.save(account2);

    }
}
