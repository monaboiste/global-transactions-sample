--liquibase formatted sql
--changeset monaboiste:employees-init

create table if not exists employees
(
    in_employee_id bigint       not null auto_increment,
    employee_id    uuid         not null,
    first_name     varchar(255) not null,
    last_name      varchar(255) not null,
    is_active      boolean default false,

    constraint employees_pk primary key (in_employee_id),
    constraint employees_ak_employee_id unique (employee_id)
);
