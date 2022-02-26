package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TestRules {
    private static final Logger logger = Logger.getLogger("");

    public static List<String> listForLogger = new ArrayList<>();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String info = String.format("Тест %s затратил %d миллисекунд на выполнение", description.getMethodName(),
                    TimeUnit.NANOSECONDS.toMillis(nanos));
            listForLogger.add(info);
            logger.info(info);
        }
    };

    public static final ExternalResource RESOURCE = new ExternalResource() {

        @Override
        protected void after() {
            System.out.println();
            System.out.println("Полная сводка по тестам:");
            listForLogger.forEach(System.out::println);
        }
    };
}
