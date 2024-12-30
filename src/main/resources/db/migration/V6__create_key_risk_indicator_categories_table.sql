CREATE TABLE key_risk_indicator_categories (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index untuk kolom name
CREATE INDEX key_risk_indicators_code_name_index
ON key_risk_indicator_categories (name);

-- Index untuk kolom is_active
CREATE INDEX key_risk_indicator_categories_is_active_index
ON key_risk_indicator_categories (is_active);