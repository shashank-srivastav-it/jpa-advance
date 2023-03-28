package com.backend.jpaadvance.repository;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.QEmployee;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@Tag(name = "Employee Data Rest Controller", description = "CRUD operations for Employee")
@RepositoryRestResource(path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee>, QuerydslBinderCustomizer<QEmployee> {
    default void customize(QuerydslBindings bindings, QEmployee employee) {
//        bindings.bind(employee.age).all((path, values) -> {
//            try {
//                NumberPath<Integer> agePath = Expressions.numberPath(Integer.class, path.getMetadata());
//                List<Integer> ages = values.stream().flatMap(value -> Arrays.stream(value.toString().split(","))).map(Integer::parseInt).collect(Collectors.toList());
//                return Optional.of(agePath.between(ages.get(0), ages.get(1)));
//            } catch (Exception e) {
//                System.out.println(e.getMessage() + "error mesage");
//                return Optional.empty();
//            }
//        });
    }

}
