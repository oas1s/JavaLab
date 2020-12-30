package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.City;

public interface CityRepository extends JpaRepository<City,Long> {
}
