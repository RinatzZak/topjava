package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 3;
    public static final int MEAL_ID_FOR_ADMIN = START_SEQ + 7;
    public static final int NOT_FOUND = 20;

    public static final Meal breakfast = new Meal(
            MEAL_ID, LocalDateTime.of(2022, Month.FEBRUARY, 19, 9, 11), "Завтрак", 600);
    public static final Meal lunch = new Meal(
            MEAL_ID + 1, LocalDateTime.of(2022, Month.FEBRUARY, 19, 12, 42), "Обед", 777);
    public static final Meal coffeeTime = new Meal(
            MEAL_ID + 2, LocalDateTime.of(2022, Month.FEBRUARY, 19, 16, 27), "Кофе", 110);
    public static final Meal dinner = new Meal(
            MEAL_ID + 3, LocalDateTime.of(2022, Month.FEBRUARY, 19, 19, 53), "Ужин", 521);
    public static final Meal breakfastOfNextDay = new Meal(
            MEAL_ID + 4, LocalDateTime.of(2022, Month.FEBRUARY, 20, 6, 22), "Завтрак", 600);
    public static final Meal lunchOfNextDay = new Meal(
            MEAL_ID + 5, LocalDateTime.of(2022, Month.FEBRUARY, 20, 13, 0), "Обед", 800);
    public static final Meal dinnerOfNextDay = new Meal(
            MEAL_ID + 6, LocalDateTime.of(2022, Month.FEBRUARY, 20, 20, 58), "Ужин", 500);
    public static final Meal coffeeForAdmin = new Meal(
            MEAL_ID_FOR_ADMIN, LocalDateTime.of(2022, Month.FEBRUARY, 19, 5, 33), "Кофе для Админа", 500);
    public static final Meal lunchForAdmin = new Meal(
            MEAL_ID_FOR_ADMIN + 1, LocalDateTime.of(2022, Month.FEBRUARY, 19, 13, 12), "Обед для Админа", 900);
    public static final Meal dinnerForAdmin = new Meal(
            MEAL_ID_FOR_ADMIN + 2, LocalDateTime.of(2022, Month.FEBRUARY, 19, 23, 17), "Ужин для Админа", 437);

    public static Meal getNew(){
        return new Meal(null, LocalDateTime.of(2022, Month.FEBRUARY, 23, 19, 0), "NewMeal", 1000);
    }

    public static Meal getUpdated(){
        return new Meal(MEAL_ID, LocalDateTime.of(2022, Month.FEBRUARY, 24, 20, 22), "UpdateMeal", 700);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
