CREATE TABLE IF NOT EXISTS permission (
  id bigint generated always as identity primary key,
  description text not null
);