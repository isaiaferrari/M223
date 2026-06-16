package ch.samt.customers.data;

import ch.samt.customers.domain.MealGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealGroupRepository
        extends JpaRepository<MealGroup, Long> {
    MealGroup findById(long id);
}
