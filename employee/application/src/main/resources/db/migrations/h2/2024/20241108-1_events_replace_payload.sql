--liquibase formatted sql
--changeset monaboiste:events-replace-payload

alter table events alter column payload rename to event;
--rollback alter table events alter column event rename to payload;
