package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDao {
    Meal save(Meal meal);

    Collection<Meal> getAll();

    void delete(int id);

    Meal get(int id);
}
