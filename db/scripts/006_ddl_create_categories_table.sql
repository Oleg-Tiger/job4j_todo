CREATE TABLE if not exists categories (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL UNIQUE
);

comment on table categories is 'Таблица с категориями задач';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Наименование категории';
