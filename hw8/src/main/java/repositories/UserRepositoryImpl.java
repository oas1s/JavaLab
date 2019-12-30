package repositories;

import models.RowMapper;
import models.User;
import servers.ConnectionToDB;
import utills.MD5Utill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    Connection connection;

    private RowMapper<User> userRowMapper = rs ->
            new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("role")
            );

    public UserRepositoryImpl() {
        try {
            this.connection = new ConnectionToDB().getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            String sql2 = "SELECT * FROM javalab.users WHERE (name = ?)";
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            stmt2.setString(1, login);
            ResultSet resultSet = stmt2.executeQuery();
            if (resultSet.next()) {
               User user = userRowMapper.mapRow(resultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        try {
            String sql = "INSERT INTO javalab.users(`name`,`password`) VALUES(?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            MD5Utill md5Utill = new MD5Utill();
            String password = md5Utill.md5Custom(user.getPassword());
            stmt.setString(1 , user.getEmail());
            stmt.setString(2 , password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

}
