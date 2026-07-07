-- VLLM20250210120000__create_template_table.sql (ia-core-llm-service)
-- Creates LLM_TEMPLATE and LLM_TEMPLATE_PARAMETER tables
-- Author: IA
-- Dependencies: VLLM20250210103000__create_schema.sql

-- Criar tabela LLM_TEMPLATE
CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_TEMPLATE (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    titulo VARCHAR(200) NOT NULL UNIQUE,
    identificador VARCHAR(255) NOT NULL UNIQUE,
    conteudo CLOB,
    flg_exige_contexto BOOLEAN DEFAULT FALSE
);

-- Criar tabela LLM_TEMPLATE_PARAMETER
CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_TEMPLATE_PARAMETER (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    template BIGINT NOT NULL,
    CONSTRAINT fk_template_parameter_template FOREIGN KEY (template) REFERENCES LARGE_LANGUAGE_MODEL.LLM_TEMPLATE(id)
);

-- Criar índice para template_id
CREATE INDEX IF NOT EXISTS idx_template_parameter_template ON LARGE_LANGUAGE_MODEL.LLM_TEMPLATE_PARAMETER(template);