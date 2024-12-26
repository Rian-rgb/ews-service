package com.ews.service.repository;

import com.ews.service.entity.ObjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObjectCategoryRepository extends JpaRepository<ObjectCategory, UUID>,
        JpaSpecificationExecutor<ObjectCategory> {
}
