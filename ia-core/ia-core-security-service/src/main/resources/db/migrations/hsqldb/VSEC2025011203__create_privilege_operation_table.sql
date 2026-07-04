-- VSEC2025011203__create_privilege_operation_table.sql (ia-core-security-service)
-- Creates the privilege_operation table with operation enum reference
-- Author: IA
-- Dependencies: VSEC2025011200__create_security_schema.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE_OPERATION (
    id BIGINT NOT NULL,
    operation TINYINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_privilege_operation PRIMARY KEY (id)
);