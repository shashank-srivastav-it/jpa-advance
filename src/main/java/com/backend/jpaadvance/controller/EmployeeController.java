package com.backend.jpaadvance.controller;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.Filter;
import com.backend.jpaadvance.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping("/single")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.OK);
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> addEmployee(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.addEmployees(employees), HttpStatus.OK);
    }

    @GetMapping("/{firstname}/{department}")
    public List<Employee> findByFirstnameAndDepartment(@PathVariable("firstname") String firstname, @PathVariable("department") String department) {
        return employeeService.findByFirstnameAndCompany(firstname, department);
    }

    @PostMapping
    public List<Employee> getEmployees(@RequestBody List<Filter> filters) {
        return employeeService.getQueryResult(filters);
    }
}