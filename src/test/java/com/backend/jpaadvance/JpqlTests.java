package com.backend.jpaadvance;

import com.backend.jpaadvance.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaAdvanceApplication.class)
class JpqlTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    void employees_with_firstname_in_a_certain_pattern() {
        TypedQuery<Employee> query = entityManager.createQuery("Select e From Employee e where e.firstName like '%s%' ", Employee.class);
        List<Employee> resultList = query.getResultList();
        log.info("Select e From Employee e where e.firstName like '%s%' -> {}", resultList.size());
    }
}
