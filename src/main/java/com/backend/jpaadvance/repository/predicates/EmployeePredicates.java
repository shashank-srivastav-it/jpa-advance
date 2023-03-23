package com.backend.jpaadvance.repository.predicates;

import com.backend.jpaadvance.entity.QEmployee;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class EmployeePredicates {
    public static Predicate hasFirstName(String firstname) {
        return QEmployee.employee.firstName.eq(firstname);
    }

    public static Predicate containsLastName(String lastname) {
        return QEmployee.employee.lastName.contains(lastname);
    }

    public static Predicate inCompany(String company) {
        return QEmployee.employee.company.eq(company);
    }

    public static Predicate ageRange(Integer min, Integer max) {
        return QEmployee.employee.age.between(min, max);
    }
}
