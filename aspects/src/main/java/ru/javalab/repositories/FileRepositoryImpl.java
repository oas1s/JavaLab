package ru.javalab.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.javalab.models.FileModel;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryImpl implements FileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<FileModel> fileRowMapper = (row, rowMapper) ->
            FileModel.builder()
                    .id(BigInteger.valueOf(row.getLong("id")))
                    .name(row.getString("name"))
                    .name_with_extension(row.getString("name_with_extension"))
                    .size(row.getLong("size"))
                    .type(row.getString("type"))
                    .path(row.getString("path"))
                    .build();

    @Override
    public Optional<FileModel> findFileByName(String name) {
        String sql = "SELECT * FROM file WHERE name = ?";
        try{
            FileModel fileModel = jdbcTemplate.queryForObject(sql, new Object[]{name}, fileRowMapper);
            return Optional.ofNullable(fileModel);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileModel> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FileModel> findAll() {
        return null;
    }

    @Override
    public void save(FileModel entity) {
        if(!findFileByName(entity.getName()).isPresent()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO file(name, name_with_extension, size, type, path) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getName_with_extension());
                preparedStatement.setLong(3, entity.getSize());
                preparedStatement.setString(4, entity.getType());
                preparedStatement.setString(5, entity.getPath());
                return preparedStatement;
            }, keyHolder);

            entity.setId((BigInteger) keyHolder.getKey());
        }
    }

    @Override
    public void delete(Long aLong) {

    }
}
