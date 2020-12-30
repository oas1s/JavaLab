package ru.javalab.rabbitmq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.rabbitmq.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
