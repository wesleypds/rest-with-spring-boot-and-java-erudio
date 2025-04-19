CREATE TABLE IF NOT EXISTS books (
  id bigint generated always as identity primary key,
  author text not null,
  launch_date timestamp not null,
  price decimal(10,2) not null,
  title text not null
);