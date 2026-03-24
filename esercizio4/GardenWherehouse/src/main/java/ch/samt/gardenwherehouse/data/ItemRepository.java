package ch.samt.gardenwherehouse.data;

import ch.samt.gardenwherehouse.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCode(String code);
}
