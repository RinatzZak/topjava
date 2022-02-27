package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestRules {
    private static final Logger logger = LoggerFactory.getLogger("");

    private static final List<String> listForLogger = new ArrayList<>();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String info = String.format("%-30s  %20d ms", description.getMethodName(),
                    TimeUnit.NANOSECONDS.toMillis(nanos));
            listForLogger.add(info + "\n");
            logger.info(info);
        }
    };

    public static final ExternalResource RESOURCE = new ExternalResource() {

        @Override
        protected void after() {
            String log = String.join("", listForLogger);
            String part = String.format("%-20s  %32s", "NAME", "TIME");
            logger.info("\n" + "All test's:" + "\n" + part + "\n" + log);
        }
    };
}
