package com.backend.jpaadvance.repository;

import com.backend.jpaadvance.entity.Employee;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "Employee Data Rest Controller", description = "CRUD operations for Employee")
@RepositoryRestResource(path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstNameIgnoreCase(String firstName, Pageable pageable);

    List<Employee> findByAgeOrderByFirstName(Integer age, Pageable pageable);
}
