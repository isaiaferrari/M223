package ch.samt.customers.service;

import ch.samt.customers.data.CustomerRepository;
import ch.samt.customers.data.MealGroupRepository;
import ch.samt.customers.domain.MealGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealGroupService {
    private final MealGroupRepository mealGroupRepository;

    @Autowired
    public MealGroupService(MealGroupRepository mealGroupRepository) {
        this.mealGroupRepository = mealGroupRepository;
    }

    public List<MealGroup> findAll() {
        return mealGroupRepository.findAll();
    }

    public MealGroup findById(long id) {
        return mealGroupRepository.findById(id);
    }
}
