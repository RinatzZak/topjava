package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao {
    private Map<Integer, Meal> mealDaoMap = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

   public MealDaoImpl() {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(count.incrementAndGet());
        return mealDaoMap.put(meal.getId(), meal);
    }

    @Override
    public Collection<Meal> getAllMeals() {
        return mealDaoMap.values();
    }

    @Override
    public void delete(int id) {
        mealDaoMap.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealDaoMap.get(id);
    }
}
