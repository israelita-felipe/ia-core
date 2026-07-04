-- VQTZ2025011210__create_job_details_table.sql (ia-core-quartz-service)
-- Creates the Quartz qrtz_job_details table for job definitions
-- Author: IA
-- Dependencies: VQTZ2025011200__create_schema.sql

-- Note: This is the standard Quartz job_details table from the Quartz distribution
CREATE TABLE IF NOT EXISTS QUARTZ.qrtz_job_details (
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME   VARCHAR(250) NOT NULL, 
    IS_DURABLE BOOLEAN NOT NULL,
    IS_NONCONCURRENT BOOLEAN NOT NULL,
    IS_UPDATE_DATA BOOLEAN NOT NULL,
    REQUESTS_RECOVERY BOOLEAN NOT NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME, JOB_NAME,JOB_GROUP)
);

CREATE INDEX IF NOT EXISTS idx_qrtz_j_req_recovery ON QUARTZ.qrtz_job_details(SCHED_NAME, REQUESTS_RECOVERY);
CREATE INDEX IF NOT EXISTS idx_qrtz_j_grp ON QUARTZ.qrtz_job_details(SCHED_NAME, JOB_GROUP);