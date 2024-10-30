--liquibase formatted sql
--changeset monaboiste:employees-add-email

ALTER TABLE employees ADD COLUMN work_email VARCHAR(255);
UPDATE employees SET work_email='unknown@unknown.com' WHERE work_email IS NULL;
ALTER TABLE employees ALTER COLUMN work_email SET NOT NULL;
--rollback ALTER TABLE employees DROP COLUMN work_email;
