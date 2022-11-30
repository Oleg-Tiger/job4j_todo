CREATE TABLE if not exists priorities (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   priority INT
);

comment on table priorities is 'Таблица приоритетов задач';
comment on column priorities.id is 'Идентификатор приоритета';
comment on column priorities.name is 'Наименование приоритета';
comment on column priorities.priority is 'Величина приоритета';
