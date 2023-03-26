package com.backend.jpaadvance;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.entity.Employee_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaAdvanceApplication.class)
class CriteriaQueryTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void employees_with_firstname_in_a_certain_pattern() {
        // 1. Use Criteria Builder to create a Criteria Query returning the expected result object
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        // 2. Define roots for tables which are involved in the query
        Root<Employee> root = cq.from(Employee.class);

        // 3. Define Predicates using Criteria Builder
        Predicate predicate = cb.like(root.get(Employee_.FIRST_NAME), "%s%");

        // 4. Add Predicates to the Criteria Query
        cq.where(predicate);

        // 5. Build the TypedQuery using the entity manager and criteria query
        TypedQuery<Employee> query = entityManager.createQuery(cq.select(root));

        List<Employee> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList.size());
    }
}
