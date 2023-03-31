package com.backend.jpaadvance.projection;

import com.backend.jpaadvance.entity.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "employee-projection", types = {Employee.class})
public interface EmployeeProjection {
    Long getId();

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    String getEmail();

    String getAddress();

    String getPhone();
}
