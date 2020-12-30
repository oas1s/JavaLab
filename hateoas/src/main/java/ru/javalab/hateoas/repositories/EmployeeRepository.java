package ru.javalab.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javalab.hateoas.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
