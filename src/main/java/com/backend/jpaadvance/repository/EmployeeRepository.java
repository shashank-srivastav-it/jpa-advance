package com.backend.jpaadvance.repository;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.QEmployee;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>, JpaSpecificationExecutor<Employee>, QuerydslPredicateExecutor<Employee>, QuerydslBinderCustomizer<QEmployee> {
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
