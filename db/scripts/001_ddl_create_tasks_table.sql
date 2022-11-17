CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   description TEXT NOT NULL,
   created TIMESTAMP,
   done BOOLEAN NOT NULL
);

comment on table tasks is 'Таблица с задачами';
comment on column tasks.id is 'Идентификатор задачи';
comment on column tasks.name is 'Наименование задачи';
comment on column tasks.description is 'Подробное описание задачи';
comment on column tasks.created is 'Время создания задачи';
comment on column tasks.done is 'Статус задачи';

