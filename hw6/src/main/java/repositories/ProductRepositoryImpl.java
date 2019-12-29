package repositories;

import models.Message;
import models.Product;
import models.RowMapper;
import servers.ConnectionToDB;
import utills.MD5Utill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private Connection connection;

    private RowMapper<Product> productRowMapper = rs ->
            new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("coast")
            );

    @Override
    public void save(Product product) {
        try {
            connection = new ConnectionToDB().getInstance();
            String sql = "INSERT INTO javalab.products(`name`,`coast`) VALUES (? , ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1 , product.getName());
            stmt.setInt(2 , product.getCoast());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        try {
            connection = new ConnectionToDB().getInstance();
            String sql = "SELECT * FROM javalab.products WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
               Product product = productRowMapper.mapRow(resultSet);
               return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findByStartAndEnd(int start, int end) {
        try {
            List<Product> list = new ArrayList<>();
            connection = new ConnectionToDB().getInstance();
            String sql = "SELECT * FROM javalab.products LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, end - start);
            stmt.setInt(2, start);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                list.add(productRowMapper.mapRow(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try {
            connection = new ConnectionToDB().getInstance();
            String sql = "DELETE FROM javalab.products WHERE (id = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<Product> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
