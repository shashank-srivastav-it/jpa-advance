package com.backend.jpaadvance.service;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.Filter;
import com.backend.jpaadvance.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.backend.jpaadvance.repository.specifications.EmployeeGenericSpecification.getSpecificationFromFilters;
import static com.backend.jpaadvance.repository.specifications.EmployeeSpecifications.hasFirstName;
import static com.backend.jpaadvance.repository.specifications.EmployeeSpecifications.inCompany;
import static org.springframework.data.jpa.domain.Specification.where;

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
        return employeeRepository.findAll(where(hasFirstName(firstname).and(inCompany(company))));
    }

    public List<Employee> getQueryResult(List<Filter> filters) {
        if (filters.size() > 0) {
            return employeeRepository.findAll(getSpecificationFromFilters(filters));
        } else {
            return (List<Employee>) employeeRepository.findAll();
        }
    }
}