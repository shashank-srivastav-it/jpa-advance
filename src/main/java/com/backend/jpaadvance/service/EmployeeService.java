package com.backend.jpaadvance.service;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.EmployeePage;
import com.backend.jpaadvance.model.EmployeeSearch;
import com.backend.jpaadvance.repository.EmployeeCustomRepository;
import com.backend.jpaadvance.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeCustomRepository employeeCriteriaRepository;

    public Page<Employee> getEmployees(EmployeePage employeePage, EmployeeSearch employeeSearchCriteria) {
        return employeeCriteriaRepository.findAllWithFilter(employeePage, employeeSearchCriteria);
    }

    public String addEmployees(List<Employee> employees) {
        List<Employee> result = (List<Employee>) employeeRepository.saveAll(employees);
        if (result.size() == employees.size()) {
            return "Success";
        }
        return "Failed";
    }
}