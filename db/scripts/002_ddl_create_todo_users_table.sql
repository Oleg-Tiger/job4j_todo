CREATE TABLE if not exists todo_users (
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   email VARCHAR NOT NULL UNIQUE,
   password VARCHAR NOT NULL
);

comment on table todo_users is 'Таблица с пользователями';
comment on column todo_users.name is 'Имя пользователя';
comment on column todo_users.id is 'Идентификатор пользователя';
comment on column todo_users.email is 'Электронная почта пользователя';
comment on column todo_users.password is 'Пароль для входа в систему';
