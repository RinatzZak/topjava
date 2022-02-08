package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.meals;


public class InMemoryMealDao implements MealDao {
    private Map<Integer, Meal> mealDaoMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealDao() {
        meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
        }
       return mealDaoMap.put(meal.getId(), meal);
    }

    @Override
    public Collection<Meal> getAll() {
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
