package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
