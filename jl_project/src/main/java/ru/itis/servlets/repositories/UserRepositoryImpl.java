package ru.itis.servlets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.servlets.models.Role;
import ru.itis.servlets.models.State;
import ru.itis.servlets.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements CrudRepository<Integer, User> {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";
    //language=SQL
    private static final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from users";
    //language=SQL
    private static final String SQL_INSERT = "insert into users(`email`,`password`,`state`, `role`) values (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getInt("id"))
                    .email(row.getString("email"))
                    .password(row.getString("password"))
                    .state(State.valueOf(row.getString("state")))
                    .role(Role.valueOf(row.getString("role")))
                    .build();

    @Override
    public Optional<User> find(Integer id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new Object[]{email}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,entity.getEmail());
            statement.setString(2,bCryptPasswordEncoder.encode(entity.getPassword()));
            statement.setString(3,State.CONFIRMED.toString());
            statement.setString(4 ,Role.USER.toString());
            return statement;
        }, keyHolder);

        entity.setId((keyHolder.getKey()).intValue());
    }

    @Override
    public void delete(Integer integer) {

    }
}
