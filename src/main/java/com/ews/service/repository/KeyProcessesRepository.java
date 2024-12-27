package com.ews.service.repository;

import com.ews.service.entity.KeyProcesses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KeyProcessesRepository extends JpaRepository<KeyProcesses, UUID>,
        JpaSpecificationExecutor<KeyProcesses> {
}
