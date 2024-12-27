CREATE TABLE areas (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Index untuk kolom is_active
CREATE INDEX areas_is_active_index
ON areas (is_active);

-- Index untuk kolom name
CREATE INDEX areas_name_index
ON areas (name);
