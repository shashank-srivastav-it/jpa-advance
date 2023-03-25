package com.backend.jpaadvance.service;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.Employee_;
import com.backend.jpaadvance.entity.QEmployee;
import com.backend.jpaadvance.repository.EmployeeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.backend.jpaadvance.repository.predicates.EmployeePredicates.hasFirstName;
import static com.backend.jpaadvance.repository.predicates.EmployeePredicates.inCompany;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public String addEmployees(List<Employee> employees) {
        List<Employee> result = (List<Employee>) employeeRepository.saveAll(employees);
        if (result.size() == employees.size()) {
            return "Success";
        }
        return "Failed";
    }

    public List<Employee> findByFirstnameAndCompany(String firstname, String company) {
        Predicate predicate = ((BooleanExpression) inCompany(company)).and(hasFirstName(firstname));
        return (List<Employee>) employeeRepository.findAll(predicate);
    }

    public Page<Employee> getEmployeesViaRequestPredicate(Predicate predicate, String sortBy, int page, int size) {
        Sort sortObj = (Objects.nonNull(sortBy)) ? Sort.by(sortBy.split(",")) : null;
        return employeeRepository.findAll(predicate, PageRequest.of(page, size, sortObj));
    }

    public Page<Employee> getEmployeesViaBooleanBuilder(String firstname, String lastname, Integer minAge, Integer maxAge, int page, int size) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (firstname != null && !firstname.isEmpty()) {
            booleanBuilder.or(QEmployee.employee.firstName.eq(firstname));
        }
        if (lastname != null && !lastname.isEmpty()) {
            booleanBuilder.or(QEmployee.employee.lastName.eq(lastname));
        }
        if (maxAge != null && maxAge != 0 && minAge != null && minAge != 0) {
            booleanBuilder.or(QEmployee.employee.age.between(minAge, maxAge));
        }
        return employeeRepository.findAll(booleanBuilder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, Employee_.ID)));
    }
}