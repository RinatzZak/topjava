package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class UserServiceTestDataJpa extends UserServiceTest {
    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(UserTestData.USER_ID);
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.user);
        MealTestData.MEAL_MATCHER.assertMatch(user.getMeals(), MealTestData.meals);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() {
        service.getWithMeal(1);
    }
}
