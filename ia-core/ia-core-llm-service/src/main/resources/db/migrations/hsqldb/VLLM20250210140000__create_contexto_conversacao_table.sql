-- VLLM20250210140000__create_contexto_conversacao_table.sql (ia-core-llm-service)
-- Creates LLM_CONTEXTO_CONVERSAO table
-- Author: IA
-- Dependencies: VLLM20250210130000__create_agente_table.sql

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_CONTEXTO_CONVERSAO (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    mensagens CLOB,
    agente_id BIGINT,
    metadata CLOB,
    estado VARCHAR(50),
    CONSTRAINT fk_contexto_agente FOREIGN KEY (agente_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_AGENTE(id)
);