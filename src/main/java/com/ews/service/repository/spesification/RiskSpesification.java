package com.ews.service.repository.spesification;

import com.ews.service.entity.*;
import com.ews.service.repository.RiskRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RiskSpesification {

    @Autowired
    private RiskRepository riskRepository;

    public Page<Risk> getPageFilterByLikeName(String name, String sortField,
                                              String sortBy, Pageable pageable) {

        Specification<Risk> spec = (root, query, criteriaBuilder) -> {

            Join<Risk, KeyProcesses> keyProcessJoin = root.join("keyProcess", JoinType.INNER);
            Join<KeyProcesses, ObjectCategory> objectCategoryJoin = keyProcessJoin.join("objectCategory", JoinType.INNER);
            Join<ObjectCategory, Area> areaJoin = objectCategoryJoin.join("area", JoinType.INNER);
            Join<Risk, RiskStatus> riskStatusJoin = root.join("riskStatus", JoinType.INNER);

            if (name != null && !name.isEmpty()) {
                Predicate areaPredicate = criteriaBuilder.like(areaJoin.get("name"), "%" + name + "%");
                Predicate objectCategoryPredicate = criteriaBuilder.like(objectCategoryJoin.get("name"), "%" + name + "%");
                Predicate keyProcessPredicate = criteriaBuilder.like(keyProcessJoin.get("name"), "%" + name + "%");
                Predicate riskDescriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + name + "%");
                Predicate riskStatusPredicate = criteriaBuilder.like(riskStatusJoin.get("name"), "%" + name + "%");
                Predicate riskCodeNamePredicate = criteriaBuilder.like(root.get("codeName"), "%" + name + "%");

                Predicate orPredicate = criteriaBuilder.or(
                        areaPredicate,
                        objectCategoryPredicate,
                        keyProcessPredicate,
                        riskDescriptionPredicate,
                        riskStatusPredicate,
                        riskCodeNamePredicate
                );

                query.where(orPredicate);
            }

            if (sortField != null && sortBy != null) {
                Order mainOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order secondaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(mainOrder, secondaryOrder);
            }

            return query.getRestriction();
        };

        return riskRepository.findAll(spec, pageable);
    }
}
