package com.backend.jpaadvance.controller;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.EmployeePage;
import com.backend.jpaadvance.model.EmployeeSearch;
import com.backend.jpaadvance.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping("/criteria-query")
    public ResponseEntity<Page<Employee>> getEmployees(EmployeePage employeePage, EmployeeSearch employeeSearchCriteria) {
        return new ResponseEntity<>(employeeService.getEmployees(employeePage, employeeSearchCriteria), HttpStatus.OK);
    }

    @PostMapping("/single")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.OK);
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> addEmployee(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.addEmployees(employees), HttpStatus.OK);
    }
}