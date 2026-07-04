-- VSEC2025011202__create_role_table.sql (ia-core-security-service)
-- Creates the role table with name field
-- Author: IA
-- Dependencies: VSEC2025011200__create_security_schema.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_ROLE (
    id BIGINT NOT NULL,
    name VARCHAR(500) NOT NULL,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_role PRIMARY KEY (id)
);
