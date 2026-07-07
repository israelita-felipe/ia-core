-- VLLM20250210150000__create_skill_table.sql (ia-core-llm-service)
-- Creates LLM_SKILL table (updated structure)
-- Author: IA
-- Dependencies: VLLM20250210103000__create_schema.sql

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_SKILL (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    identificador VARCHAR(100) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    tipo VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    modulo_origem VARCHAR(200)
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_llm_skill_identificador ON LARGE_LANGUAGE_MODEL.LLM_SKILL(identificador);
CREATE UNIQUE INDEX IF NOT EXISTS uq_llm_skill_titulo ON LARGE_LANGUAGE_MODEL.LLM_SKILL(titulo);