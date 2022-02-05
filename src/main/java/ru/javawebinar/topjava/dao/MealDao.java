package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDao {
    Meal save(Meal meal);

    Collection<Meal> getAllMeals();

    void delete(int id);

    Meal get(int id);
}
