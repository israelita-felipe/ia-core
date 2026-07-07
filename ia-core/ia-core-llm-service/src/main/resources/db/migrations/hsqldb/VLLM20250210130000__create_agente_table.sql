-- VLLM20250210130000__create_agente_table.sql (ia-core-llm-service)
-- Creates LLM_AGENTE, LLM_AGENTE_FERRAMENTA, and LLM_AGENTE_SKILL tables
-- Author: IA
-- Dependencies: VLLM20250210110000__create_prompt_ferramenta_skill_tables.sql, VLLM20250210120000__create_template_table.sql

-- Migration para criação da tabela LLM_AGENTE e tabelas de relacionamento
-- Implementa o padrão ia-core para spring-ai-agent-utils (banco de dados em vez de YAML)

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    identificador VARCHAR(100) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    instrucoes CLOB,
    modelo VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    CONSTRAINT uq_llm_agente_identificador UNIQUE (identificador)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE_FERRAMENTA (
    agente_id BIGINT NOT NULL,
    ferramenta_id BIGINT NOT NULL,
    PRIMARY KEY (agente_id, ferramenta_id),
    CONSTRAINT fk_agente_ferramenta_agente FOREIGN KEY (agente_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_AGENTE(id),
    CONSTRAINT fk_agente_ferramenta_ferramenta FOREIGN KEY (ferramenta_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA(id)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE_SKILL (
    agente_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (agente_id, skill_id),
    CONSTRAINT fk_agente_skill_agente FOREIGN KEY (agente_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_AGENTE(id),
    CONSTRAINT fk_agente_skill_skill FOREIGN KEY (skill_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_SKILL(id)
);