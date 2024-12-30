CREATE TABLE risks (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    code_name VARCHAR(50) NOT NULL,
    description NVARCHAR2(150) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    key_process_id RAW(36),
    CONSTRAINT risks_key_process_id_foreign FOREIGN KEY (key_process_id) REFERENCES key_processes(id),
    risk_status_id RAW(36),
    CONSTRAINT risks_risk_status_id_foreign FOREIGN KEY (risk_status_id) REFERENCES risk_statuses(id),
    CONSTRAINT risks_code_name_unique UNIQUE (code_name)
);

-- Index untuk kolom is_active
CREATE INDEX risks_is_active_index
ON risks (is_active);

-- Index untuk kolom code_name
CREATE INDEX risks_code_name_index
ON risks (code_name);

-- Index untuk kolom description
CREATE INDEX risks_description_index
ON risks (description);