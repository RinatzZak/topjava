package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 10, 0), "Кофе для Админа", 500), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 13, 0), "Обед для Админа", 700), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 21, 0), "Ужин для Админа", 600), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 24, 9, 11), "Кофе для Админа", 500), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 24, 12, 22), "Обед для Админа", 700), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 24, 22, 33), "Ужин для Админа", 600), 2);
    }


    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? null : mealMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        return getFiltered(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startTime, endTime));
    }

    private List<Meal> getFiltered(int userId, Predicate<Meal> predicate) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? Collections.emptyList() : mealMap.values().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

