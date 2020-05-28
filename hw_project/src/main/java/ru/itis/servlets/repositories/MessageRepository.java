package ru.itis.servlets.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.servlets.dto.MessageDto;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface MessageRepository extends JpaRepository<MessageDto,Integer> {
    List<MessageDto> findAllById(int Id);
    Page<MessageDto> findAll(Pageable pageable);
}
