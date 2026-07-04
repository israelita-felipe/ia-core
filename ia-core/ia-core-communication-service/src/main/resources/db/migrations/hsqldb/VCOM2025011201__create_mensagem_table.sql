-- VCOM2025011201__create_mensagem_table.sql (ia-core-communication-service)
-- Creates the mensagem table for message communication
-- Author: IA
-- Dependencies: VCOM2025011200__create_schema.sql

CREATE TABLE IF NOT EXISTS COMMUNICATION.COM_MENSAGEM (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    telefone_destinatario VARCHAR(20) NOT NULL,
    nome_destinatario VARCHAR(100),
    corpo_mensagem CLOB NOT NULL,
    tipo_canal VARCHAR(50) NOT NULL,
    status_mensagem VARCHAR(50) NOT NULL,
    id_externo VARCHAR(100),
    data_envio TIMESTAMP,
    data_entrega TIMESTAMP,
    data_leitura TIMESTAMP,
    motivo_falha VARCHAR(500)
);

CREATE INDEX IF NOT EXISTS idx_com_mensagem_tipo_canal ON COMMUNICATION.COM_MENSAGEM(tipo_canal);
CREATE INDEX IF NOT EXISTS idx_com_mensagem_status ON COMMUNICATION.COM_MENSAGEM(status_mensagem);
CREATE INDEX IF NOT EXISTS idx_com_mensagem_telefone ON COMMUNICATION.COM_MENSAGEM(telefone_destinatario);
CREATE INDEX IF NOT EXISTS idx_com_mensagem_data_envio ON COMMUNICATION.COM_MENSAGEM(data_envio);