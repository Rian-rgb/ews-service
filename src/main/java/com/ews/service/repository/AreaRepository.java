package com.ews.service.repository;

import com.ews.service.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {

    @Query("SELECT E FROM Area E WHERE (name LIKE CONCAT('%', :name, '%') OR :name is NULL)")
    Page<Area> findPage(String name, Pageable pageable);
}
