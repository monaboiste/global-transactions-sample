--liquibase formatted sql
--changeset monaboiste:employees-init

create table if not exists employees
(
    in_employee_id bigint       not null primary key auto_increment,
    employee_id    uuid         not null unique,
    first_name     varchar(255) not null,
    last_name      varchar(255) not null,
    is_active      boolean default false
);
