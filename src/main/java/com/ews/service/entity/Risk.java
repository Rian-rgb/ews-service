package com.ews.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "risks")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "code_name", length = 50, nullable = false)
    private String codeName;

    @Column(name = "description", columnDefinition = "NVARCHAR2(150)", nullable = false)
    private String description;

    @Column(name = "is_active", columnDefinition = "tinyint", nullable = false)
    private Byte isActive;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_process_id", referencedColumnName="id")
    private KeyProcesses keyProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_status_id", referencedColumnName="id")
    private RiskStatus riskStatus;

}
