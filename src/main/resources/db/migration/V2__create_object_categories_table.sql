CREATE TABLE object_categories (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100),
    is_active NUMBER(3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    area_id RAW(16),
    CONSTRAINT fk_area FOREIGN KEY (area_id) REFERENCES areas(id)
);

-- Index untuk kolom is_active
CREATE INDEX idx_is_active
ON object_categories (is_active);

-- Index untuk kolom name
CREATE INDEX idx_name
ON object_categories (name);