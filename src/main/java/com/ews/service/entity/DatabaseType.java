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
@Table(name = "database_types")
public class DatabaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "name", nullable = false, length = 100, columnDefinition = "nvarchar(100)")
    private String name;

    @Column(name = "connection", nullable = false, length = 100, columnDefinition = "nvarchar(100)")
    private String connection;

    @Column(name = "is_active", columnDefinition = "tinyint", nullable = false)
    private Byte isActive;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "db_host", length = 191, columnDefinition = "nvarchar(191)")
    private String dbHost;

    @Column(name = "db_port", length = 191, columnDefinition = "nvarchar(191)")
    private String dbPort;

    @Column(name = "db_name", length = 191, columnDefinition = "nvarchar(191)")
    private String dbName;

    @Column(name = "db_user_name", length = 191, columnDefinition = "nvarchar(191)")
    private String dbUserName;

    @Column(name = "db_pass", length = 191, columnDefinition = "nvarchar(191)")
    private String dbPass;

    @Column(name = "connection_name", length = 191, columnDefinition = "nvarchar(191)")
    private String connectionName;

}
