-- VLLM20250210160000__add_agente_fields.sql (ia-core-llm-service)
-- Migration for adding temperature and max_tokens columns to LLM_AGENTE
-- Author: IA
-- Dependencies: VLLM20250210130000__create_agente_table.sql

ALTER TABLE IF EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE
ADD COLUMN temperature DOUBLE DEFAULT 0.7;

ALTER TABLE IF EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE
ADD COLUMN max_tokens INTEGER DEFAULT 2048;