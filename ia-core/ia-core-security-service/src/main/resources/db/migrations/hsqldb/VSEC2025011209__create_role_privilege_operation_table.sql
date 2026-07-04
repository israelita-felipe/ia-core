-- VSEC2025011209__create_role_privilege_operation_table.sql (ia-core-security-service)
-- Creates the join table for role_privilege-privilege_operation many-to-many relationship
-- Author: IA
-- Dependencies: VSEC2025011202__create_privilege_operation_table.sql, VSEC2025011207__create_role_privilege_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_ROLE_PRIVILEGE_OPERATION (
    role_privilege BIGINT NOT NULL,
    privilege_operation BIGINT NOT NULL,
    CONSTRAINT fk_role_privilege_operation_role_privilege FOREIGN KEY (role_privilege) REFERENCES SECURITY.SEC_ROLE_PRIVILEGE(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_privilege_operation_privilege_operation FOREIGN KEY (privilege_operation) REFERENCES SECURITY.SEC_PRIVILEGE_OPERATION(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sec_role_privilege_operation ON SECURITY.SEC_ROLE_PRIVILEGE_OPERATION(role_privilege, privilege_operation);
CREATE INDEX IF NOT EXISTS idx_sec_role_privilege_operation_role_privilege_id ON SECURITY.SEC_ROLE_PRIVILEGE_OPERATION(role_privilege);
CREATE INDEX IF NOT EXISTS idx_sec_role_privilege_operation_privilege_operation_id ON SECURITY.SEC_ROLE_PRIVILEGE_OPERATION(privilege_operation);