package com.backend.jpaadvance.repository.specification;

import com.backend.jpaadvance.model.FilterRequest;
import com.backend.jpaadvance.model.SearchRequest;
import com.backend.jpaadvance.model.SortRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.backend.jpaadvance.enumeration.FieldType.BOOLEAN;
import static com.backend.jpaadvance.enumeration.FieldType.CHAR;
import static com.backend.jpaadvance.enumeration.FieldType.DATE;
import static com.backend.jpaadvance.enumeration.FieldType.DOUBLE;
import static com.backend.jpaadvance.enumeration.FieldType.INTEGER;
import static com.backend.jpaadvance.enumeration.FieldType.LONG;
import static com.backend.jpaadvance.enumeration.FieldType.STRING;

@Slf4j
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {
    private SearchRequest request;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

        for (FilterRequest filter : request.getFilters()) {
            log.info("Filter: {} {} {}", filter.getKey(), filter.getOperator().toString(), filter.getValue());
            setFieldType(filter, root);
            predicate = filter.getOperator().build(root, cb, filter, predicate);
        }

        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : request.getSorts()) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }

        query.orderBy(orders);
        return predicate;
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 5));
    }

    public static <T> void setFieldType(FilterRequest filter, Root<T> root) {
        Class<?> type = root.get(filter.getKey()).getJavaType();
        if (type.isAssignableFrom(Double.class)) {
            filter.setFieldType(DOUBLE);
        } else if (type.isAssignableFrom(Integer.class)) {
            filter.setFieldType(INTEGER);
        } else if (type.isAssignableFrom(Boolean.class)) {
            filter.setFieldType(BOOLEAN);
        } else if (type.isAssignableFrom(Long.class)) {
            filter.setFieldType(LONG);
        } else if (type.isAssignableFrom(Date.class)) {
            filter.setFieldType(DATE);
        } else if (type.isAssignableFrom(Character.class)) {
            filter.setFieldType(CHAR);
        } else {
            filter.setFieldType(STRING);
        }
    }
}
