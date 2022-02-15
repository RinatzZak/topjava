package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<Meal> getBetweenHalfOpen(LocalDate localDateStart, LocalDate localDateEnd, int userId) {
        if (localDateStart == null) {
            localDateStart = LocalDate.of(1900, Month.JANUARY, 1);
        }
        if (localDateEnd == null) {
            localDateEnd = LocalDate.of(2500, Month.DECEMBER, 31);
        }
        return repository.getBetweenHalfOpen(localDateStart.atStartOfDay(), localDateEnd.plusDays(1).atStartOfDay(), userId);
    }
}