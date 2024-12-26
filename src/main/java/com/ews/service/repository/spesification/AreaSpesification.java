package com.ews.service.repository.spesification;

import com.ews.service.entity.Area;
import com.ews.service.repository.AreaRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AreaSpesification {

    @Autowired
    private AreaRepository areaRepository;

    public Page<Area> getPageFilterByLikeName(String name, String sortField,
                                              String sortBy, Pageable pageable) {

        Specification<Area> spec = (root, query, criteriaBuilder) -> {

            if (name != null) {
                Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                query.where(nameLike);
            }

            if (sortField != null && sortBy != null) {
                Order order = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order orderId = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(order, orderId);
            }

            return query.getRestriction();
        };

        return areaRepository.findAll(spec, pageable);

    }

    public Page<Area> getPageFilterByLikeNameAndStatusActive(String name, String sortField,
                                              String sortBy, Pageable pageable) {

        Specification<Area> spec = (root, query, criteriaBuilder) -> {

            Predicate isActiveEqual = criteriaBuilder.equal(root.get("isActive"), 1);

            Predicate conditions = criteriaBuilder.and(isActiveEqual);

            if (name != null) {
                Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                conditions = criteriaBuilder.and(conditions, nameLike);
            }

            query.where(conditions);

            if (sortField != null && sortBy != null) {
                Order order = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order orderId = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(order, orderId);
            }

            return query.getRestriction();
        };

        return areaRepository.findAll(spec, pageable);

    }
}
