CREATE TABLE key_risk_indicators (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    code_name VARCHAR(50) NOT NULL,
    description NVARCHAR2(150) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    query_content NVARCHAR2(4000) NOT NULL,
    overdue NUMBER,
    last_run TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    record NUMBER,
    distribution_key NVARCHAR2(100),
    unique_key NVARCHAR2(100),
    due_date NUMBER,
    next_running TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    running_by NVARCHAR2(50),
    permission_type NVARCHAR2(191),
    delete_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    risk_id RAW(36) NOT NULL,
    CONSTRAINT key_risk_indicators_risk_id_foreign FOREIGN KEY (risk_id) REFERENCES risks(id),
    key_risk_indicator_category_id RAW(36) NOT NULL,
    CONSTRAINT key_risk_indicators_key_risk_indicator_category_id_foreign FOREIGN KEY (key_risk_indicator_category_id) REFERENCES key_risk_indicator_categories(id),
    database_type_id RAW(36) NOT NULL,
    CONSTRAINT key_risk_indicators_database_type_id_foreign FOREIGN KEY (database_type_id) REFERENCES database_types(id),
    status_id RAW(36),
    CONSTRAINT key_risk_indicators_key_risk_indicator_status_id_foreign FOREIGN KEY (status_id) REFERENCES key_risk_indicator_statuses(id),
    CONSTRAINT key_risk_indicators_code_name_unique UNIQUE (code_name)
);

-- Index untuk kolom code_name
CREATE INDEX key_risk_indicators_code_name_index
ON key_risk_indicators (code_name);

-- Index untuk kolom description
CREATE INDEX key_risk_indicators_description_index
ON key_risk_indicators (description);

-- Index untuk kolom last_run
CREATE INDEX key_risk_indicators_last_run_index
ON key_risk_indicators (last_run);