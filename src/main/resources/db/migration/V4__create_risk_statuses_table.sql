CREATE TABLE risk_statuses (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(50) NOT NULL,
    order_number INTEGER
);

-- Index untuk kolom name
CREATE INDEX risk_statuses_name_index
ON risk_statuses (name);