-- VQTZ2025011202__create_periodicidade_table.sql (ia-core-quartz-service)
-- Creates the periodicidade table with RFC 5545 recurrence rules
-- Author: IA
-- Dependencies: VQTZ2025011200__create_schema.sql

-- Periodicidade é uma entidade com múltiplos embedded objects e coleções
-- para suporte a RFC 5545 (iCalendar)
CREATE TABLE IF NOT EXISTS QUARTZ.QRTZ_PERIODICIDADE (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT DEFAULT 1 NOT NULL,
    start_date DATE,
    start_time TIME,
    end_date DATE,
    end_time TIME,
    zone_id VARCHAR(255),
    ativo BOOLEAN DEFAULT TRUE
);

-- Evento (EXCEPTION DATE) - datas específicas a serem excluídas
CREATE TABLE IF NOT EXISTS QUARTZ.QRTZ_PERIODICIDADE_EXCEPTION_DATE (
    periodicidade_id BIGINT NOT NULL,
    exception_date DATE,
    CONSTRAINT fk_exc_date_periodicidade FOREIGN KEY (periodicidade_id) REFERENCES QUARTZ.QRTZ_PERIODICIDADE(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_periodicidade_exception_date ON QUARTZ.QRTZ_PERIODICIDADE_EXCEPTION_DATE(periodicidade_id, exception_date);

-- Evento (INCLUDE DATE) - datas específicas a serem incluídas
CREATE TABLE IF NOT EXISTS QUARTZ.QRTZ_PERIODICIDADE_INCLUDE_DATE (
    periodicidade_id BIGINT NOT NULL,
    include_date DATE,
    CONSTRAINT fk_inc_date_periodicidade FOREIGN KEY (periodicidade_id) REFERENCES QUARTZ.QRTZ_PERIODICIDADE(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_periodicidade_include_date ON QUARTZ.QRTZ_PERIODICIDADE_INCLUDE_DATE(periodicidade_id, include_date);
