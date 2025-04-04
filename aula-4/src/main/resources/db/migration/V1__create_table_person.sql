CREATE TABLE IF NOT EXISTS person (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    gender VARCHAR(9),
    address VARCHAR(100) NOT NULL
);