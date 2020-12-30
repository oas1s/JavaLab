package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.PickUpStore;

public interface PickUpStoreRepository extends JpaRepository<PickUpStore,Long> {
}
