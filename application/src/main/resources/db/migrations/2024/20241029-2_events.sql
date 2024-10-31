--liquibase formatted sql
--changeset monaboiste:events-init

create table if not exists events
(
    in_event_id    bigint       not null primary key auto_increment,
    event_id       uuid         not null unique,
    name           varchar(255) not null,
    occurred_at    timestamp    not null,
    aggregate_id   varchar(255) not null,
    aggregate_type varchar(255) not null,
    payload        binary large object
);
