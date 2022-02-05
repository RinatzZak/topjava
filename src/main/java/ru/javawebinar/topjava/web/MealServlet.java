package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final int CALORIES_PER_DAY = 2000;

    private MealDao mealDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealDao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("mealList", MealsUtil.getCollectionWithMealTo(mealDao.getAllMeals(), CALORIES_PER_DAY));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else if (action.equals("delete")) {
            String paramId = req.getParameter("id").trim();
            int id = Integer.parseInt(paramId);
            mealDao.delete(id);
            resp.sendRedirect("meals");
        } else if (action.equals("create")) {
            Meal meal = new Meal(LocalDateTime.now(), "", 0);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("/mealCreate.jsp").forward(req, resp);
        } else if (action.equals("edit")) {
            String paramId = req.getParameter("id").trim();
            int id = Integer.parseInt(paramId);
            req.setAttribute("meal", mealDao.get(id));
            req.getRequestDispatcher("/mealEdit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(null, LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description").trim()
                , Integer.parseInt(req.getParameter("calories")));
        mealDao.save(meal);
        resp.sendRedirect("meals");
    }
}
