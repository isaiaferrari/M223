package ch.samt.springtransaction.data;

import ch.samt.springtransaction.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository
        extends JpaRepository<Account, String> {
}
