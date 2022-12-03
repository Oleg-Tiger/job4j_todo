CREATE TABLE if not exists tasks_categories (
   id SERIAL PRIMARY KEY,
   task_id int NOT NULL REFERENCES tasks(id),
   category_id int NOT NULL REFERENCES categories(id)
);

comment on table tasks_categories is 'Таблица для связи many-to-many задач и категорий';
comment on column tasks_categories.task_id is 'Идентификатор задачи';
comment on column tasks_categories.category_id is 'Идентификатор категории';