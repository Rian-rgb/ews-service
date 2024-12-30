package com.ews.service.repository;

import com.ews.service.entity.Area;
import com.ews.service.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RiskRepository extends JpaRepository<Risk, UUID>, JpaSpecificationExecutor<Risk> {
}
