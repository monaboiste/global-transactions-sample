--liquibase formatted sql
--changeset monaboiste:employees-add-version

alter table employees add column tcn int default 0;
--rollback alter table employees drop column ver;
