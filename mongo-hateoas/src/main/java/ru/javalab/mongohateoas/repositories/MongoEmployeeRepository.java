package ru.javalab.mongohateoas.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.javalab.mongohateoas.model.Employee;

import java.util.List;

public interface MongoEmployeeRepository extends org.springframework.data.mongodb.repository.MongoRepository<Employee, String> {


    @RestResource(path = "itisstud", rel = "itisstud")
    @Query(value = "{education: itis, $or: [{job: {$exists: true}}]}")
    List<Employee> find(Pageable pageable);
}