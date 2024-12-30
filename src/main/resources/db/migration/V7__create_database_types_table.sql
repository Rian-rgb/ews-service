CREATE TABLE database_types (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    connection NVARCHAR2(100) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    db_host NVARCHAR2(191),
    db_port NVARCHAR2(191),
    db_name NVARCHAR2(191),
    db_user_name NVARCHAR2(191),
    db_pass NVARCHAR2(191),
    connection_name NVARCHAR2(191)
);

-- Index untuk kolom name
CREATE INDEX key_risk_indicator_categories_name_index
ON database_types (name);

-- Index untuk kolom is_active
CREATE INDEX key_risk_indicator_categories_is_active_index
ON database_types (is_active);