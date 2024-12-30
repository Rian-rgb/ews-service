CREATE TABLE object_categories (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    area_id RAW(36) NOT NULL,
    CONSTRAINT object_categories_area_id_foreign FOREIGN KEY (area_id) REFERENCES areas(id)
);

-- Index untuk kolom is_active
CREATE INDEX object_categories_is_active_index
ON object_categories (is_active);

-- Index untuk kolom name
CREATE INDEX object_categories_name_index
ON object_categories (name);