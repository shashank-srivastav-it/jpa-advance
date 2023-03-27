package com.backend.jpaadvance.controller;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.EmployeePage;
import com.backend.jpaadvance.model.EmployeeSearch;
import com.backend.jpaadvance.service.EmployeeService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{firstname}/{department}")
    public List<Employee> findByFirstnameAndDepartment(@PathVariable("firstname") String firstname, @PathVariable("department") String department) {
        return employeeService.findByFirstnameAndCompany(firstname, department);
    }

    @GetMapping("/query-predicate")
    public Page<Employee> getEmployeesViaRequestPredicate(@QuerydslPredicate(root = Employee.class) Predicate predicate,
                                                          @RequestParam(value = "sortBy", required = false) String sortBy,
                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "5") int size) {
        return employeeService.getEmployeesViaRequestPredicate(predicate, sortBy, page, size);
    }

    @PostMapping("/boolean-builder")
    public Page<Employee> getEmployeesViaBooleanBuilder(EmployeeSearch employeeSearch, EmployeePage employeePage) {
        return employeeService.getEmployeesViaBooleanBuilder(employeeSearch, employeePage);
    }

    //    Sample url
    // http://localhost:8080/employee/query-predicate?gender=female&age=20&sortBy=firstName&size=4
    //http://localhost:8080/employee/boolean-builder?minAge=20&maxAge=30&sortBy=firstName&size=4
}