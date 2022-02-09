package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final int caloriesPerDay = 2000;

    private MealDao mealDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealDao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "null";
        }
        switch (action.toLowerCase()) {
            case "delete":
                mealDao.delete(getId(req));
                log.debug("delete meal with id={}", getId(req));
                resp.sendRedirect("meals");
                break;
            case "create":
            case "edit":
                Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealDao.get(getId(req));
                log.debug(meal.getId() == null ? "create new {}" : "update {}", meal);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealCreateAndUpdate.jsp").forward(req, resp);
                break;
            case "null":
            default:
                log.debug("redirect to meals");
                req.setAttribute("mealList", MealsUtil.getTos(mealDao.getAll(), caloriesPerDay));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        log.debug(meal.getId() == null ? "create new {}" : "update {}", meal);
        mealDao.save(meal);
        resp.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
