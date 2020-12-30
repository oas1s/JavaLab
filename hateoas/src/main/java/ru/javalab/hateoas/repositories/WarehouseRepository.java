package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
