package com.backend.jpaadvance.repository;

import com.backend.jpaadvance.entity.Employee;
import com.backend.jpaadvance.model.EmployeePage;
import com.backend.jpaadvance.model.EmployeeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeCustomRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Employee> findAllWithFilter(EmployeePage employeePage, EmployeeSearch employeeSearch) {
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Predicate predicate = getPredicate(employeeSearch, employeeRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());
        Pageable pageable = getPageable(employeePage);

        long employeesCount = getEmployeeCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(EmployeeSearch employeeSearchCriteria, Root<Employee> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(employeeSearchCriteria.getFirstName())) {
            predicates.add(criteriaBuilder.like(employeeRoot.get("firstName"), "%" + employeeSearchCriteria.getFirstName() + "%"));
        }
        if (Objects.nonNull(employeeSearchCriteria.getLastName())) {
            predicates.add(criteriaBuilder.like(employeeRoot.get("lastName"), "%" + employeeSearchCriteria.getLastName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Pageable getPageable(EmployeePage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }

    private long getEmployeeCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
