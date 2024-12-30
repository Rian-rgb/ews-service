package com.ews.service.repository.spesification;

import com.ews.service.entity.RiskStatus;
import com.ews.service.repository.RiskStatusRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RiskStatusSpesification {

    @Autowired
    private RiskStatusRepository riskStatusRepository;

    public Page<RiskStatus> getPageFilterByStatusActive(String name, Pageable pageable) {

        Specification<RiskStatus> spec = (root, query, criteriaBuilder) -> {

            if (name != null && !name.isEmpty()) {
                Predicate nameLikePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                query.where(nameLikePredicate);
            }

            return query.getRestriction();
        };

        return riskStatusRepository.findAll(spec, pageable);
    }
}
