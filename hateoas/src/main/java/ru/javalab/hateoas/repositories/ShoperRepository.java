package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.Shoper;

public interface ShoperRepository extends JpaRepository<Shoper,Long> {
}
