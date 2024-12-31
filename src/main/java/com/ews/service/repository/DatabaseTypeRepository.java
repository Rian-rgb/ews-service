package com.ews.service.repository;

import com.ews.service.entity.DatabaseType;
import com.ews.service.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DatabaseTypeRepository extends JpaRepository<DatabaseType, UUID>,
        JpaSpecificationExecutor<DatabaseType> {
    Optional<DatabaseType> findByConnectionName(String connectionName);
}
