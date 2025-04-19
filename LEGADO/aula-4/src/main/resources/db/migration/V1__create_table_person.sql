CREATE TABLE IF NOT EXISTS person (
    id bigint generated always as identity primary key,
    first_name text not null,
    last_name text not null,
    gender text,
    address text
);