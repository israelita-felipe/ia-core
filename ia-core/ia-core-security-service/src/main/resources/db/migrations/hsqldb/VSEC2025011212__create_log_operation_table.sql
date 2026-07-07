-- VSEC2025011212__create_log_operation_table.sql (ia-core-security-service)
-- Creates the log_operation table for audit logging of security operations
-- Author: IA
-- Dependencies: VSEC2025011200__create_security_schema.sql

CREATE TABLE IF NOT EXISTS SECURITY.SEC_LOG_OPERATION (
    id BIGINT NOT NULL,
    user_name VARCHAR(500) NOT NULL,
    user_code VARCHAR(500) NOT NULL,
    value_id BIGINT,
    type VARCHAR(500),
    old_value CLOB,
    new_value CLOB,
    date_time_operation TIMESTAMP,
    operation TINYINT,
    version BIGINT NOT NULL DEFAULT 1,
    CONSTRAINT pk_sec_log_operation PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_sec_log_operation_type_value_id_date_time_operation ON SECURITY.SEC_LOG_OPERATION(type ASC, value_id ASC, date_time_operation DESC);
