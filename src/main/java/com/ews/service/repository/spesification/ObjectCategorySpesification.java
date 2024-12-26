package com.ews.service.repository.spesification;

import com.ews.service.entity.Area;
import com.ews.service.entity.ObjectCategory;
import com.ews.service.repository.ObjectCategoryRepository;
import com.ews.service.response.objectcategory.GetListObjectForReportResponse;
import com.ews.service.response.objectcategory.GetObjectCategoryResponse;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ObjectCategorySpesification {

    @Autowired
    private ObjectCategoryRepository objectCategoryRepository;

    public Page<GetObjectCategoryResponse> getPageFilterByLikeName(String name, String sortField,
                                                                   String sortBy, Pageable pageable) {

        Specification<ObjectCategory> spec = (root, query, criteriaBuilder) -> {

            Join<ObjectCategory, Area> areaJoin = root.join("area", JoinType.INNER);

            Predicate conditions = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                Predicate nameLike = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder
                                .lower(root.get("name")), "%" + name.toLowerCase() + "%"),

                        criteriaBuilder.like(criteriaBuilder
                                .lower(areaJoin.get("name")), "%" + name.toLowerCase() + "%")
                );
                conditions = criteriaBuilder.and(conditions, nameLike);
            }

            if (sortField != null && sortBy != null) {
                Order primaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order secondaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(primaryOrder, secondaryOrder);
            }

            query.where(conditions);
            return query.getRestriction();
        };

        return objectCategoryRepository.findAll(spec, pageable)
                .map(objectCategory -> new GetObjectCategoryResponse(
                        objectCategory.getId(),
                        objectCategory.getName(),
                        objectCategory.getIsActive(),
                        objectCategory.getArea().getName()
                ));

    }

    public Page<GetObjectCategoryResponse> getPageFilterByLikeNameAndStatusActive(String name, String sortField,
                                                                                  String sortBy, Pageable pageable) {

        Specification<ObjectCategory> spec = (root, query, criteriaBuilder) -> {

            Join<ObjectCategory, Area> areaJoin = root.join("area", JoinType.INNER);

            Predicate conditions = criteriaBuilder.conjunction();

            Predicate isActiveEqual = criteriaBuilder.equal(root.get("isActive"), 1);
            conditions = criteriaBuilder.and(conditions, isActiveEqual);

            if (name != null && !name.isEmpty()) {
                Predicate nameLike = criteriaBuilder
                        .like(criteriaBuilder.lower(areaJoin.get("name")), "%" + name.toLowerCase() + "%");
                conditions = criteriaBuilder.and(conditions, nameLike);
            }

            if (sortField != null && sortBy != null) {
                Order primaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order secondaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(primaryOrder, secondaryOrder);
            }

            query.where(conditions);
            return query.getRestriction();
        };

        return objectCategoryRepository.findAll(spec, pageable)
                .map(objectCategory -> new GetObjectCategoryResponse(
                        objectCategory.getId(),
                        objectCategory.getName(),
                        objectCategory.getIsActive(),
                        objectCategory.getArea().getName()
                ));

    }

    public Page<GetListObjectForReportResponse> getPageFilterByLikeNameAndAreaIdAndStatusActive(
            String name, String sortField,
            String sortBy, Pageable pageable,
            UUID segmentId) {

        Specification<ObjectCategory> spec = (root, query, criteriaBuilder) -> {

            Join<ObjectCategory, Area> areaJoin = root.join("area", JoinType.INNER);

            Predicate conditions = criteriaBuilder.conjunction();

            Predicate isActiveEqual = criteriaBuilder.equal(root.get("isActive"), 1);
            conditions = criteriaBuilder.and(conditions, isActiveEqual);

            if (name != null && !name.isEmpty()) {
                Predicate nameLike = criteriaBuilder
                        .like(criteriaBuilder.lower(areaJoin.get("name")), "%" + name.toLowerCase() + "%");
                conditions = criteriaBuilder.and(conditions, nameLike);
            }

            if (segmentId != null && !segmentId.equals("all")) {
                Predicate segmentIdEqual = criteriaBuilder.equal(root.get("area").get("id"), segmentId);
                conditions = criteriaBuilder.and(conditions, segmentIdEqual);
            }

            if (sortField != null && sortBy != null) {
                Order primaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get(sortField))
                        : criteriaBuilder.desc(root.get(sortField));

                Order secondaryOrder = sortBy.equalsIgnoreCase("asc")
                        ? criteriaBuilder.asc(root.get("id"))
                        : criteriaBuilder.desc(root.get("id"));

                query.orderBy(primaryOrder, secondaryOrder);
            }

            query.where(conditions);
            return query.getRestriction();
        };

        return objectCategoryRepository.findAll(spec, pageable)
                .map(objectCategory -> new GetListObjectForReportResponse(
                        objectCategory.getId().toString(),
                        objectCategory.getName()
                ));

    }

}
