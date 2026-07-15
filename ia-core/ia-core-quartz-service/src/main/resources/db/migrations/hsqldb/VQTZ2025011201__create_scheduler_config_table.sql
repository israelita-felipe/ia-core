-- VQTZ2025011201__create_scheduler_config_table.sql (ia-core-quartz-service)
-- Creates the scheduler_config table for job scheduling configuration
-- Author: IA
-- Dependencies: VQTZ2025011200__create_schema.sql

CREATE TABLE IF NOT EXISTS QUARTZ.QRTZ_SCHEDULER_CONFIG (
    id BIGINT NOT NULL,
    version BIGINT DEFAULT 1 NOT NULL,
    job_class_name VARCHAR(250) NOT NULL,
    periodicidade BIGINT NOT NULL,
    CONSTRAINT pk_qrtz_scheduler_config PRIMARY KEY (id),
    CONSTRAINT fk_scheduler_config_periodicidade FOREIGN KEY (periodicidade) REFERENCES QUARTZ.QRTZ_PERIODICIDADE(id)
);

CREATE INDEX IF NOT EXISTS idx_qrtz_scheduler_config_job_class ON QUARTZ.QRTZ_SCHEDULER_CONFIG(job_class_name);
