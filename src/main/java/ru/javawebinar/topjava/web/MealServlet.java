package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = context.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        context.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id.isEmpty()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, getId(request));
        }

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "filter":
                LocalDate startDate = String.valueOf(request.getParameter("startDate")).length() == 0 ? null :
                        LocalDate.parse(request.getParameter("startDate"));
                LocalDate endDate = String.valueOf(request.getParameter("endDate")).length() == 0 ? null :
                        LocalDate.parse(request.getParameter("endDate"));
                LocalTime startTime = String.valueOf(request.getParameter("startTime")).length() == 0 ? null :
                        LocalTime.parse(request.getParameter("startTime"));
                LocalTime endTime = String.valueOf(request.getParameter("endTime")).length() == 0 ? null :
                        LocalTime.parse(request.getParameter("endTime"));
                request.setAttribute("meals", mealRestController.getBetweenHalfOpen(startDate, startTime, endDate,
                        endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
