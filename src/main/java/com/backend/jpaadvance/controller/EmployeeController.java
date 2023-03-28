package com.backend.jpaadvance.controller;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.SearchRequest;
import com.backend.jpaadvance.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
@Tag(name = "Employee Controller")
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping("/multiple")
    public ResponseEntity<?> addEmployee(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.addEmployees(employees), HttpStatus.OK);
    }

    @PostMapping(value = "/search")
    public Page<Employee> search(@RequestBody SearchRequest request) {
        return employeeService.searchOperatingSystem(request);
    }
}