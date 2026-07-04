-- VCOM2025011202__create_grupo_contato_table.sql (ia-core-communication-service)
-- Creates the grupo_contato table for contact groups
-- Author: IA
-- Dependencies: VCOM2025011200__create_schema.sql

CREATE TABLE IF NOT EXISTS COMMUNICATION.COM_GRUPO_CONTATO (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_com_grupo_contato_nome UNIQUE (nome)
);