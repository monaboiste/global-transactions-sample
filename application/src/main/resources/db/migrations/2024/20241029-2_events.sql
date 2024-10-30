--liquibase formatted sql
--changeset monaboiste:events-init

CREATE TABLE IF NOT EXISTS events (
    in_event_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    event_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    aggregate_id VARCHAR(255) NOT NULL,
    aggregate_type VARCHAR(255) NOT NULL,
    payload BINARY LARGE OBJECT
);
