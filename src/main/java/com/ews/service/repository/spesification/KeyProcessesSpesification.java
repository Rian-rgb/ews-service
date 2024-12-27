package com.ews.service.repository.spesification;

import com.ews.service.entity.Area;
import com.ews.service.entity.KeyProcesses;
import com.ews.service.entity.ObjectCategory;
import com.ews.service.repository.KeyProcessesRepository;
import com.ews.service.response.keyprocesses.GetKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetListKeyProcessesResponse;
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
public class KeyProcessesSpesification {

    @Autowired
    private KeyProcessesRepository keyProcessesRepository;

    public Page<GetKeyProcessesResponse> getPageFilterByLikeName(String name, String sortField,
                                                                 String sortBy, Pageable pageable) {

        Specification<KeyProcesses> spec = (root, query, criteriaBuilder) -> {

            Join<KeyProcesses, ObjectCategory> objectCategoryJoin = root.join("objectCategory", JoinType.INNER);
            Join<ObjectCategory, Area> areaJoin = objectCategoryJoin.join("area", JoinType.INNER);

            Predicate conditions = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                Predicate nameFilter = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(objectCategoryJoin.get("name")), "%" + name.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(areaJoin.get("name")), "%" + name.toLowerCase() + "%")
                );
                conditions = criteriaBuilder.and(conditions, nameFilter);
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

        return keyProcessesRepository.findAll(spec, pageable)
                .map(keyProcess -> GetKeyProcessesResponse.builder()
                        .id(keyProcess.getId())
                        .name(keyProcess.getName())
                        .isActive(keyProcess.getIsActive())
                        .areaName(keyProcess.getObjectCategory().getArea().getName())
                        .objectCategoryName(keyProcess.getObjectCategory().getName())
                        .build());
    }

    public Page<GetListKeyProcessesResponse> getActiveKeyProcesses(String name, Pageable pageable) {

        Specification<KeyProcesses> spec = (root, query, criteriaBuilder) -> {

            Predicate isActive = criteriaBuilder.equal(root.get("isActive"), 1);

            if (name != null && !name.isEmpty()) {
                Predicate filterByName = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
                return criteriaBuilder.and(isActive, filterByName);
            }

            return isActive;
        };

        return keyProcessesRepository.findAll(spec, pageable)
                .map(keyProcess -> new GetListKeyProcessesResponse(keyProcess.getId(), keyProcess.getName()));
    }
}
