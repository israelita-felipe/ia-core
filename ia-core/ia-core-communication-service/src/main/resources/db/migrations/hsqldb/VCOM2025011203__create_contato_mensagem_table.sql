-- VCOM2025011203__create_contato_mensagem_table.sql (ia-core-communication-service)
-- Creates the contato_mensagem table for contact-message association
-- Author: IA
-- Dependencies: VCOM2025011202__create_grupo_contato_table.sql

CREATE TABLE IF NOT EXISTS COMMUNICATION.COM_CONTATO_MENSAGEM (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    grupo_contato_id BIGINT NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    nome VARCHAR(100),
    CONSTRAINT fk_contato_mensagem_grupo FOREIGN KEY (grupo_contato_id) REFERENCES COMMUNICATION.COM_GRUPO_CONTATO(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_com_contato_mensagem_grupo ON COMMUNICATION.COM_CONTATO_MENSAGEM(grupo_contato_id);
CREATE INDEX IF NOT EXISTS idx_com_contato_mensagem_telefone ON COMMUNICATION.COM_CONTATO_MENSAGEM(telefone);