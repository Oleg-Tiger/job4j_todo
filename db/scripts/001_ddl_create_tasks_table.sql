CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT NOT NULL,
   created TIMESTAMP,
   done BOOLEAN NOT NULL
);