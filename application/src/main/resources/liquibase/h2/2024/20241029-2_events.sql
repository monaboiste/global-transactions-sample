--liquibase formatted sql
--changeset monaboiste:events-init

create table if not exists events
(
    in_event_id    bigint       not null auto_increment,
    event_id       uuid         not null,
    name           varchar(255) not null,
    occurred_at    timestamp    not null,
    aggregate_id   varchar(255) not null,
    aggregate_type varchar(255) not null,
    payload        binary large object,

    constraint events_pk primary key (in_event_id),
    constraint events_ak_event_id unique (event_id)
);
