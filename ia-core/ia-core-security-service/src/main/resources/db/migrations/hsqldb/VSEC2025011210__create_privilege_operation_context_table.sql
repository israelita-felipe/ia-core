-- VSEC2025011210__create_privilege_operation_context_table.sql (ia-core-security-service)
-- Creates the privilege_operation_context table with context key and values
-- Author: IA
-- Dependencies: VSEC2025011202__create_privilege_operation_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE_OPERATION_CONTEXT (
    id BIGINT NOT NULL,
    privilege_operation BIGINT NOT NULL,
    context_key VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_privilege_operation_context PRIMARY KEY (id),
    CONSTRAINT fk_privilege_operation_context_privilege_operation FOREIGN KEY (privilege_operation) REFERENCES SECURITY.SEC_PRIVILEGE_OPERATION(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sec_privilege_operation_context_privilege_operation_id ON SECURITY.SEC_PRIVILEGE_OPERATION_CONTEXT(privilege_operation);

-- Privilege operation context values stored in separate table per entity PrivilegeOperationContext.PRIVILEGE_OPERATION_CONTEXT_VALUE_TABLE_NAME
CREATE TABLE IF NOT EXISTS SECURITY.SEC_PRIVILEGE_OPERATION_CONTEXT_VALUE (
    privilege_operation_context BIGINT NOT NULL,
    privilege_operation_context_value VARCHAR(255),
    CONSTRAINT fk_privilege_operation_context_value_context FOREIGN KEY (privilege_operation_context) REFERENCES SECURITY.SEC_PRIVILEGE_OPERATION_CONTEXT(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sec_privilege_operation_context_value_context_id ON SECURITY.SEC_PRIVILEGE_OPERATION_CONTEXT_VALUE(privilege_operation_context);