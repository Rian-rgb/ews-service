CREATE TABLE areas (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    name NVARCHAR2(100),
    is_active NUMBER(3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Index untuk kolom is_active
CREATE INDEX idx_is_active
ON ExampleTable (is_active);

-- Index untuk kolom name
CREATE INDEX idx_name
ON ExampleTable (name);
