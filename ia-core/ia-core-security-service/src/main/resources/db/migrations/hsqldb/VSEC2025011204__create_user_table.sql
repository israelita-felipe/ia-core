-- VSEC2025011204__create_user_table.sql (ia-core-security-service)
-- Creates the user table with authentication fields
-- Author: IA
-- Dependencies: VSEC2025011200__create_security_schema.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_USER (
    id BIGINT NOT NULL,
    user_name VARCHAR(500) NOT NULL,
    user_code VARCHAR(500) NOT NULL,
    password VARCHAR(500),
    enabled BOOLEAN NOT NULL,
    account_not_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_not_locked BOOLEAN NOT NULL DEFAULT FALSE,
    credentials_not_expired BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sec_user_code ON SECURITY.SEC_USER(user_code);
