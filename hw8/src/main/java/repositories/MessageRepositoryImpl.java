package repositories;

import models.Message;
import models.RowMapper;
import servers.ConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryImpl implements MessageRepository {
    private Connection connection;

    private RowMapper<Message> messageRowMapper = rs ->
            new Message(
                    rs.getInt("id"),
                    rs.getInt("id_from"),
                    rs.getString("text")
            );

    public MessageRepositoryImpl() {
        try {
            this.connection = new ConnectionToDB().getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> findByStartAndEnd(int start, int end) {
        try {
            List<Message> list = new ArrayList<>();
            String sql = "SELECT * FROM javalab.messages LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, end-start);
            stmt.setInt(2, start);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                list.add(messageRowMapper.mapRow(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(Message message) {
        try {
            String sql = "INSERT INTO javalab.messages(`text`,`id_from`,`date`) VALUES(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            Date date = new Date(new java.util.Date().getTime());
            stmt.setString(1 , message.getText());
            stmt.setInt(2 , message.getFrom_id());
            stmt.setDate(3, date);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Message> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
