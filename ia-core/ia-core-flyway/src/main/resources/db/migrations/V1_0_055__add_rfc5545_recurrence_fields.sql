-- Flyway migration: Add RFC 5545 recurrence fields and IntervaloTemporal improvements
-- This migration adds support for advanced recurrence rules according to RFC 5545 (iCalendar)
-- and improves IntervaloTemporal to support separate date and time fields.
--
-- New fields added:
-- - WKST (weekStartDay): Day of week that starts the week count
-- - BYYEARDAY: Days within the year
-- - BYWEEKNO: Weeks within the year
-- - BYHOUR, BYMINUTE, BYSECOND: Time-based filters
-- - ExclusaoRecorrencia (EXRULE): Exclusion rules for recurrences
-- - IntervaloTemporal: Separate date and time fields
--
-- Author: Israel Araújo
-- Date: 2026-02-13

-- ============================================
-- Recorrencia (RRULE) - Add new columns
-- ============================================

-- Add WKST column to IA_RECORRENCIA
ALTER TABLE IA_RECORRENCIA ADD COLUMN week_start_day VARCHAR(2);

-- Add BYYEARDAY table for IA_RECORRENCIA_DIA_ANO
CREATE TABLE IA_RECORRENCIA_DIA_ANO (
    IA_RECORRENCIA_ID BIGINT NOT NULL,
    by_year_day INTEGER NOT NULL,
    PRIMARY KEY (IA_RECORRENCIA_ID, by_year_day)
);

-- Add BYWEEKNO table for IA_RECORRENCIA_SEMANA_ANO
CREATE TABLE IA_RECORRENCIA_SEMANA_ANO (
    IA_RECORRENCIA_ID BIGINT NOT NULL,
    by_week_no INTEGER NOT NULL,
    PRIMARY KEY (IA_RECORRENCIA_ID, by_week_no)
);

-- Add BYHOUR table for IA_RECORRENCIA_HORA
CREATE TABLE IA_RECORRENCIA_HORA (
    IA_RECORRENCIA_ID BIGINT NOT NULL,
    by_hour INTEGER NOT NULL,
    PRIMARY KEY (IA_RECORRENCIA_ID, by_hour)
);

-- Add BYMINUTE table for IA_RECORRENCIA_MINUTO
CREATE TABLE IA_RECORRENCIA_MINUTO (
    IA_RECORRENCIA_ID BIGINT NOT NULL,
    by_minute INTEGER NOT NULL,
    PRIMARY KEY (IA_RECORRENCIA_ID, by_minute)
);

-- Add BYSECOND table for IA_RECORRENCIA_SEGUNDO
CREATE TABLE IA_RECORRENCIA_SEGUNDO (
    IA_RECORRENCIA_ID BIGINT NOT NULL,
    by_second INTEGER NOT NULL,
    PRIMARY KEY (IA_RECORRENCIA_ID, by_second)
);

-- ============================================
-- Periodicidade - Add EXRULE embedded field
-- ============================================

-- Add EXCLUSAO_RECORRENCIA_ID to IA_PERIODICIDADE
ALTER TABLE IA_PERIODICIDADE ADD COLUMN exclusao_recorrencia_id BIGINT;

-- Create IA_EXCLUSAO_RECORRENCIA table
CREATE TABLE IA_EXCLUSAO_RECORRENCIA (
    id BIGINT NOT NULL PRIMARY KEY,
    ex_frequency VARCHAR(20),
    ex_interval_value INTEGER,
    ex_by_set_position INTEGER,
    ex_until_date DATE,
    ex_count_limit INTEGER,
    ex_week_start_day VARCHAR(2)
);

-- Add foreign key constraint
ALTER TABLE IA_PERIODICIDADE ADD CONSTRAINT fk_periodicidade_exclusao_recorrencia 
FOREIGN KEY (exclusao_recorrencia_id) REFERENCES IA_EXCLUSAO_RECORRENCIA(id);

-- Create IA_EXC_RECORRENCIA_DIA_SEMANA table
CREATE TABLE IA_EXC_RECORRENCIA_DIA_SEMANA (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_day VARCHAR(2) NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_day)
);

-- Create IA_EXC_RECORRENCIA_DIA_MES table
CREATE TABLE IA_EXC_RECORRENCIA_DIA_MES (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_month_day INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_month_day)
);

-- Create IA_EXC_RECORRENCIA_MES table
CREATE TABLE IA_EXC_RECORRENCIA_MES (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_month VARCHAR(20) NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_month)
);

-- Create IA_EXC_RECORRENCIA_DIA_ANO table
CREATE TABLE IA_EXC_RECORRENCIA_DIA_ANO (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_year_day INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_year_day)
);

-- Create IA_EXC_RECORRENCIA_SEMANA_ANO table
CREATE TABLE IA_EXC_RECORRENCIA_SEMANA_ANO (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_week_no INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_week_no)
);

-- Create IA_EXC_RECORRENCIA_HORA table
CREATE TABLE IA_EXC_RECORRENCIA_HORA (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_hour INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_hour)
);

-- Create IA_EXC_RECORRENCIA_MINUTO table
CREATE TABLE IA_EXC_RECORRENCIA_MINUTO (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_minute INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_minute)
);

-- Create IA_EXC_RECORRENCIA_SEGUNDO table
CREATE TABLE IA_EXC_RECORRENCIA_SEGUNDO (
    IA_EXCLUSAO_RECORRENCIA_ID BIGINT NOT NULL,
    ex_by_second INTEGER NOT NULL,
    PRIMARY KEY (IA_EXCLUSAO_RECORRENCIA_ID, ex_by_second)
);

-- Add indexes for better performance
CREATE INDEX idx_recorrencia_dia_ano_recorrencia_id ON IA_RECORRENCIA_DIA_ANO(IA_RECORRENCIA_ID);
CREATE INDEX idx_recorrencia_semana_ano_recorrencia_id ON IA_RECORRENCIA_SEMANA_ANO(IA_RECORRENCIA_ID);
CREATE INDEX idx_recorrencia_hora_recorrencia_id ON IA_RECORRENCIA_HORA(IA_RECORRENCIA_ID);
CREATE INDEX idx_recorrencia_minuto_recorrencia_id ON IA_RECORRENCIA_MINUTO(IA_RECORRENCIA_ID);
CREATE INDEX idx_recorrencia_segundo_recorrencia_id ON IA_RECORRENCIA_SEGUNDO(IA_RECORRENCIA_ID);
CREATE INDEX idx_exclusao_recorrencia_periodicidade_id ON IA_PERIODICIDADE(exclusao_recorrencia_id);

-- ============================================
-- IntervaloTemporal - Separate date and time (handled by JPA)
-- The JPA entity now uses separate fields for date and time
-- This is handled by Hibernate schema auto-generation
-- ============================================
