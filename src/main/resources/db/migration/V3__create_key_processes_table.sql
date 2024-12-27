CREATE TABLE key_processes (
    id RAW(36) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100) NOT NULL,
    is_active NUMBER(3) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    object_category_id RAW(16),
    CONSTRAINT fk_object_category FOREIGN KEY (object_category_id) REFERENCES object_categories(id)
);

-- Index untuk kolom is_active
CREATE INDEX key_processes_is_active_index
ON key_processes (is_active);

-- Index untuk kolom name
CREATE INDEX key_processes_name_index
ON key_processes (name);