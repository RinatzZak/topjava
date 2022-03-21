package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.javawebinar.topjava.util.ValidationUtil.validate;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final UserMapper userMapper = new UserMapper();

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password,
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }
        addRoles(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRolesForOne(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRolesForOne(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return setRoles(users);
    }

    private void addRoles(User user) {
        Set<Role> roles = user.getRoles();
        jdbcTemplate.batchUpdate("INSERT INTO user_roles(user_id, role) VALUES (?, ?)", roles, roles.size(),
                (ps, argument) -> {
                    ps.setInt(1, user.getId());
                    ps.setString(2, argument.name());
                });
    }

    private List<User> setRoles(List<User> users) {
        Map<Integer, Set<Role>> roles = jdbcTemplate.query("SELECT * FROM user_roles", userMapper);
        users.forEach(user -> user.setRoles(roles.get(user.getId())));
        return users;
    }

    private User setRolesForOne(User user) {
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles  WHERE user_id=?",
                    (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.getId());
            user.setRoles(roles);
        }
        return user;
    }

    public static class UserMapper implements ResultSetExtractor<Map<Integer, Set<Role>>> {
        @Override
        public Map<Integer, Set<Role>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Set<Role>> roles = new HashMap<>();
            while (rs.next()) {
                Integer id = rs.getInt("user_id");
                if (roles.get(id) == null) {
                    roles.putIfAbsent(id, new HashSet<>());
                }
                Role role = Role.valueOf(rs.getString("role"));
                roles.get(id).add(role);
            }
            return roles;
        }
    }
}
