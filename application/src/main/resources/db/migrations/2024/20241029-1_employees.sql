--liquibase formatted sql
--changeset monaboiste:employees-init

CREATE TABLE IF NOT EXISTS employees (
    in_employee_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id UUID NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT false
);
