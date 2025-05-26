CREATE TABLE IF NOT EXISTS person (
  id bigint generated always as identity primary key,
  first_name text NOT NULL,
  last_name text NOT NULL,
  address text NOT NULL,
  gender text NOT NULL
);