-- VSEC2025011211__create_role_privileges_table.sql (ia-core-security-service)
-- Creates the join table for role-privilege many-to-many relationship
-- Author: IA
-- Dependencies: VSEC2025011200__create_privilege_table.sql, VSEC2025011201__create_role_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_ROLES_PRIVILEGES (
    privilege_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_roles_privileges_privilege FOREIGN KEY (privilege_id) REFERENCES SECURITY.SEC_PRIVILEGE(id) ON DELETE CASCADE,
    CONSTRAINT fk_roles_privileges_role FOREIGN KEY (role_id) REFERENCES SECURITY.SEC_ROLE(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sec_roles_privileges ON SECURITY.SEC_ROLES_PRIVILEGES(role_id, privilege_id);