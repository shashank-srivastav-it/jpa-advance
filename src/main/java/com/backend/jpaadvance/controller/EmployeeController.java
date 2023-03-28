package com.backend.jpaadvance.controller;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.Filter;
import com.backend.jpaadvance.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/employees")
@AllArgsConstructor
@Tag(name = "Employee Controller")
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping("/multiple")
    public ResponseEntity<?> addEmployee(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.addEmployees(employees), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public List<Employee> getEmployees(@RequestBody List<Filter> filters) {
        return employeeService.getQueryResult(filters);
    }

    @GetMapping("/{firstname}/{company}")
    public List<Employee> findByFirstnameAndCompany(@PathVariable("firstname") String firstname, @PathVariable("company") String company) {
        return employeeService.findByFirstnameAndCompany(firstname, company);
    }
}