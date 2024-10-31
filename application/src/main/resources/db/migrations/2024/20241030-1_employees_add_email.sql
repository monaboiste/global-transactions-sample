--liquibase formatted sql
--changeset monaboiste:employees-add-email

alter table employees add column work_email varchar(255);

update employees
set work_email='unknown@unknown.com'
where work_email is null;

alter table employees alter column work_email set not null;
--rollback ALTER TABLE employees DROP COLUMN work_email;
