package com.backend.jpaadvance.repository.specifications;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.Employee_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSpecifications {
    public static Specification<Employee> hasFirstName(String firstname) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Employee_.firstName), firstname);
        });
    }

    public static Specification<Employee> containsLastName(String lastname) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Employee_.lastName), "%" + lastname + "%");
        });
    }

    public static Specification<Employee> inCompany(String company) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Employee_.company), company);
        });
    }
}
