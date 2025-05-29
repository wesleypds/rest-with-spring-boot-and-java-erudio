CREATE TABLE books (
  id bigint generated always as identity primary key,
  author text,
  launch_date timestamp NOT NULL,
  price decimal(65,2) NOT NULL,
  title text
);
