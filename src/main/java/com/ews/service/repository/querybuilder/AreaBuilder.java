package com.ews.service.repository.querybuilder;

import com.ews.service.model.Area;
import com.ews.service.repository.AreaRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AreaBuilder {

    @Autowired
    private AreaRepository areaRepository;

    public Page<Area> getPageAreasFilterByLikeName(String name, String sortField,
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

}
