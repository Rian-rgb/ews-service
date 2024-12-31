package com.ews.service.repository.spesification;

import com.ews.service.entity.Area;
import com.ews.service.entity.DatabaseType;
import com.ews.service.repository.AreaRepository;
import com.ews.service.repository.DatabaseTypeRepository;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTypeSpesification {

    @Autowired
    private DatabaseTypeRepository databaseTypeRepository;

    public Page<DatabaseType> getPageFilterByLikeName(String name, String sortField, String sortBy, Pageable pageable) {
        Specification<DatabaseType> spec = (Root<DatabaseType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.conjunction();

            // Filter for `name`
            if (name != null && !name.isEmpty()) {
                Predicate nameLike = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("connectionName"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("connection"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("dbHost"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("dbPort"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("dbName"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("dbUserName"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("dbPass"), "%" + name + "%")
                );
                finalPredicate = criteriaBuilder.and(finalPredicate, nameLike);
            }

            if (sortField != null && sortBy != null) {

                if (sortField.equalsIgnoreCase("no")) {
                    query.orderBy(
                            sortBy.equalsIgnoreCase("asc") ?
                                    criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")),
                            sortBy.equalsIgnoreCase("asc") ?
                                    criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id"))
                    );
                } else {
                    query.orderBy(
                            sortBy.equalsIgnoreCase("asc") ?
                                    criteriaBuilder.asc(root.get(sortField)) : criteriaBuilder.desc(root.get(sortField)),
                            sortBy.equalsIgnoreCase("asc") ?
                                    criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id"))
                    );
                }
            }

            return finalPredicate;
        };

        return databaseTypeRepository.findAll(spec, pageable);
    }
}
