CREATE TABLE IF NOT EXISTS users (
  id bigint generated always as identity primary key,
  user_name text unique,
  full_name text default null,
  password text default null,
  account_non_expired boolean default null,
  account_non_locked boolean default null,
  credentials_non_expired boolean default null,
  enabled boolean default null
);