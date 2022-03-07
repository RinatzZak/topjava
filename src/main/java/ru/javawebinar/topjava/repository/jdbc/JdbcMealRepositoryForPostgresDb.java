package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@Repository
@Profile(POSTGRES_DB)
public class JdbcMealRepositoryForPostgresDb extends JdbcMealRepository{
    public JdbcMealRepositoryForPostgresDb(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public LocalDateTime LtdForHSQL(LocalDateTime ldt) {
        return ldt;
    }

}
