-- VSEC2025011205__create_user_roles_table.sql (ia-core-security-service)
-- Creates the join table for user-role many-to-many relationship
-- Author: IA
-- Dependencies: VSEC2025011202__create_role_table.sql, VSEC2025011204__create_user_table.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_USERS_ROLES (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES SECURITY.SEC_USER(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES SECURITY.SEC_ROLE(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sec_users_roles ON SECURITY.SEC_USERS_ROLES(user_id, role_id);