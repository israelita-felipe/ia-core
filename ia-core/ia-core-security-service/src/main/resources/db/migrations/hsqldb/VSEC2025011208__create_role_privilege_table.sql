-- VSEC2025011208__create_role_privilege_table.sql (ia-core-security-service)
-- Creates the role_privilege table linking roles to privileges with operations
-- Author: IA
-- Dependencies: VSEC2025011200__create_privilege_table.sql, VSEC2025011201__create_role_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_ROLE_PRIVILEGE (
    id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    privilege_id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_role_privilege PRIMARY KEY (id),
    CONSTRAINT fk_role_privilege_role FOREIGN KEY (role_id) REFERENCES SECURITY.SEC_ROLE(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_privilege_privilege FOREIGN KEY (privilege_id) REFERENCES SECURITY.SEC_PRIVILEGE(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sec_role_privilege_role_id ON SECURITY.SEC_ROLE_PRIVILEGE(role_id);
CREATE INDEX IF NOT EXISTS idx_sec_role_privilege_privilege_id ON SECURITY.SEC_ROLE_PRIVILEGE(privilege_id);