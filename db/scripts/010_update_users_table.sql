ALTER TABLE todo_users ADD COLUMN userZone TEXT;

comment on column todo_users.userZone is 'Идентификатор часового пояса пользователя';