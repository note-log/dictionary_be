create table major
(
    id         bigint primary key auto_increment not null,
    name       varchar(128) unique               not null,
    is_deleted boolean
) engine = InnoDB
  default charset = utf8mb4;

create table clazz
(
    id         bigint primary key auto_increment not null,
    name       varchar(128) unique               not null,
    is_deleted boolean
) engine = InnoDB
  default charset = utf8mb4;

