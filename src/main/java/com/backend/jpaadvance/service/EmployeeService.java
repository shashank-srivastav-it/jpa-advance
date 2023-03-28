package com.backend.jpaadvance.service;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.Employee_;
import com.backend.jpaadvance.entity.QEmployee;
import com.backend.jpaadvance.model.EmployeePage;
import com.backend.jpaadvance.model.EmployeeSearch;
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
import java.util.Optional;

import static com.backend.jpaadvance.repository.predicates.EmployeePredicates.hasFirstName;
import static com.backend.jpaadvance.repository.predicates.EmployeePredicates.inCompany;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

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
        Sort sortObj = Objects.nonNull(sortBy) ? Sort.by(sortBy.split(",")) : Sort.by(Employee_.FIRST_NAME);
        return employeeRepository.findAll(predicate, PageRequest.of(page, size, sortObj));
    }

    public Page<Employee> getEmployeesViaBooleanBuilder(EmployeeSearch employeeSearch, EmployeePage employeePage) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(employeeSearch.getFirstName())) {
            booleanBuilder.and(QEmployee.employee.firstName.eq(employeeSearch.getFirstName()));
        }
        if (Objects.nonNull(employeeSearch.getLastName())) {
            booleanBuilder.and(QEmployee.employee.lastName.eq(employeeSearch.getLastName()));
        }
        if (Objects.nonNull(employeeSearch.getMaxAge()) && employeeSearch.getMaxAge() != 0 && Objects.nonNull(employeeSearch.getMinAge()) && employeeSearch.getMinAge() != 0) {
            booleanBuilder.and(QEmployee.employee.age.between(employeeSearch.getMinAge(), employeeSearch.getMaxAge()));
        }
        return employeeRepository.findAll(booleanBuilder.getValue(), PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), Sort.by(employeePage.getSortDirection(), employeePage.getSortBy())));
    }
}