package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    Integer delete(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);


    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("startDateTime") LocalDateTime startDateTime,
                          @Param("endDateTime") LocalDateTime endDateTime,
                          @Param("userId") int userId);

}
