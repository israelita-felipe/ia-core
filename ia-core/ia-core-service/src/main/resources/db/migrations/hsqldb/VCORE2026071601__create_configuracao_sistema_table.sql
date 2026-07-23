-- Create schema for core configuration
CREATE SCHEMA IF NOT EXISTS core;

-- Create configuracao_sistema table
CREATE TABLE core.configuracao_sistema (
    id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 1,
    chave VARCHAR(255) NOT NULL UNIQUE,
    valor TEXT NOT NULL,
    modulo VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50) NOT NULL,
    CONSTRAINT pk_configuracao_sistema PRIMARY KEY (id)
);

-- Create indexes for performance
CREATE INDEX idx_configuracao_sistema_modulo ON core.configuracao_sistema (modulo);
CREATE INDEX idx_configuracao_sistema_categoria ON core.configuracao_sistema (categoria);
CREATE INDEX idx_configuracao_sistema_tipo ON core.configuracao_sistema (tipo);