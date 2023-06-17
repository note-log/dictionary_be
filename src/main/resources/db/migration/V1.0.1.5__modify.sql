alter table major modify column is_deleted boolean not null default false;
alter table clazz modify column is_deleted boolean not null default false;