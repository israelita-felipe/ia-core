-- VSEC2025011201__create_privilege_table.sql (ia-core-security-service)
-- Creates the privilege table with type enum and context values
-- Author: IA
-- Dependencies: VSEC2025011200__create_security_schema.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE (
    id BIGINT NOT NULL,
    name VARCHAR(500) NOT NULL UNIQUE,
    type INTEGER NOT NULL DEFAULT 1,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_privilege PRIMARY KEY (id)
);

-- Privilege context values stored in separate table per entity Privilege.PRIVILEGE_CONTEXT_TABLE_NAME
CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE_CONTEXT (
    privilege_id BIGINT NOT NULL,
    context VARCHAR(255),
    CONSTRAINT fk_sec_privilege_context_privilege FOREIGN KEY (privilege_id) REFERENCES SECURITY.SEC_PRIVILEGE(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sec_privilege_context_privilege_id ON SECURITY.SEC_PRIVILEGE_CONTEXT(privilege_id);
