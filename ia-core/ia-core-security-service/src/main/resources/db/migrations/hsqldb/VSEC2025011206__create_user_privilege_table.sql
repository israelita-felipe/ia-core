-- VSEC2025011206__create_user_privilege_table.sql (ia-core-security-service)
-- Creates the user_privilege table linking users to privileges with operations
-- Author: IA
-- Dependencies: VSEC2025011200__create_privilege_table.sql, VSEC2025011203__create_user_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_USER_PRIVILEGE (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    privilege_id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_user_privilege PRIMARY KEY (id),
    CONSTRAINT fk_user_privilege_user FOREIGN KEY (user_id) REFERENCES SECURITY.SEC_USER(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_privilege_privilege FOREIGN KEY (privilege_id) REFERENCES SECURITY.SEC_PRIVILEGE(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_sec_user_privilege_user_id ON SECURITY.SEC_USER_PRIVILEGE(user_id);
CREATE INDEX IF NOT EXISTS idx_sec_user_privilege_privilege_id ON SECURITY.SEC_USER_PRIVILEGE(privilege_id);