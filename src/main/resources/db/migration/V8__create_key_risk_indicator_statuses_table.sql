CREATE TABLE key_risk_indicator_statuses (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index untuk kolom name
CREATE INDEX key_risk_indicator_statuses_name_index
ON key_risk_indicator_statuses (name);