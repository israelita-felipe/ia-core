-- Migration para criação de tabelas LLM
-- Baseado no ADR-048 (Embed AI MCP Skills with Spring AI)

-- Tabela de Chat Memory
CREATE TABLE IF NOT EXISTS chat_memory (
    id VARCHAR(36) NOT NULL,
    conversation_id VARCHAR(255) NOT NULL,
    message_type VARCHAR(50) NOT NULL,
    message_content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_chat_memory_conversation_id ON chat_memory(conversation_id);

-- Tabela de Agente
CREATE TABLE IF NOT EXISTS llm_agente (
    id BIGINT NOT NULL,
    identificador VARCHAR(100) NOT NULL UNIQUE,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    instrucoes TEXT,
    modelo VARCHAR(100),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_llm_agente_identificador ON llm_agente(identificador);
CREATE INDEX IF NOT EXISTS idx_llm_agente_ativo ON llm_agente(ativo);

-- Tabela de Ferramenta
CREATE TABLE IF NOT EXISTS llm_ferramenta (
    id BIGINT NOT NULL,
    identificador VARCHAR(100) NOT NULL UNIQUE,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    tipo VARCHAR(50) NOT NULL,
    classe_ferramenta VARCHAR(255),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_llm_ferramenta_identificador ON llm_ferramenta(identificador);
CREATE INDEX IF NOT EXISTS idx_llm_ferramenta_tipo ON llm_ferramenta(tipo);
CREATE INDEX IF NOT EXISTS idx_llm_ferramenta_ativo ON llm_ferramenta(ativo);

-- Tabela de Skill
CREATE TABLE IF NOT EXISTS llm_skill (
    id BIGINT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    instrucoes TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_llm_skill_titulo ON llm_skill(titulo);
CREATE INDEX IF NOT EXISTS idx_llm_skill_ativo ON llm_skill(ativo);

-- Tabela de Prompt
CREATE TABLE IF NOT EXISTS llm_prompt (
    id BIGINT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    finalidade VARCHAR(50) NOT NULL,
    conteudo TEXT NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_llm_prompt_titulo ON llm_prompt(titulo);
CREATE INDEX IF NOT EXISTS idx_llm_prompt_finalidade ON llm_prompt(finalidade);
CREATE INDEX IF NOT EXISTS idx_llm_prompt_ativo ON llm_prompt(ativo);

-- Tabela de Template
CREATE TABLE IF NOT EXISTS llm_template (
    id BIGINT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(1000),
    conteudo TEXT NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    modulo_origem VARCHAR(200),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_llm_template_titulo ON llm_template(titulo);
CREATE INDEX IF NOT EXISTS idx_llm_template_ativo ON llm_template(ativo);

-- Tabela de Template Parameter
CREATE TABLE IF NOT EXISTS llm_template_parameter (
    id BIGINT NOT NULL,
    template_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descricao VARCHAR(500),
    obrigatorio BOOLEAN NOT NULL DEFAULT FALSE,
    valor_padrao VARCHAR(500),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_template_parameter_template FOREIGN KEY (template_id) REFERENCES llm_template(id)
);

CREATE INDEX IF NOT EXISTS idx_llm_template_parameter_template_id ON llm_template_parameter(template_id);
CREATE INDEX IF NOT EXISTS idx_llm_template_parameter_nome ON llm_template_parameter(nome);

-- Tabela de Associação Agente-Ferramenta
CREATE TABLE IF NOT EXISTS llm_agente_ferramenta (
    agente_id BIGINT NOT NULL,
    ferramenta_id BIGINT NOT NULL,
    PRIMARY KEY (agente_id, ferramenta_id),
    CONSTRAINT fk_agente_ferramenta_agente FOREIGN KEY (agente_id) REFERENCES llm_agente(id),
    CONSTRAINT fk_agente_ferramenta_ferramenta FOREIGN KEY (ferramenta_id) REFERENCES llm_ferramenta(id)
);

-- Tabela de Associação Agente-Skill
CREATE TABLE IF NOT EXISTS llm_agente_skill (
    agente_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (agente_id, skill_id),
    CONSTRAINT fk_agente_skill_agente FOREIGN KEY (agente_id) REFERENCES llm_agente(id),
    CONSTRAINT fk_agente_skill_skill FOREIGN KEY (skill_id) REFERENCES llm_skill(id)
);

-- Tabela de Associação Skill-Ferramenta
CREATE TABLE IF NOT EXISTS llm_skill_ferramenta (
    skill_id BIGINT NOT NULL,
    ferramenta_id BIGINT NOT NULL,
    PRIMARY KEY (skill_id, ferramenta_id),
    CONSTRAINT fk_skill_ferramenta_skill FOREIGN KEY (skill_id) REFERENCES llm_skill(id),
    CONSTRAINT fk_skill_ferramenta_ferramenta FOREIGN KEY (ferramenta_id) REFERENCES llm_ferramenta(id)
);

-- Tabela de Log de Interação AI
CREATE TABLE IF NOT EXISTS llm_ai_interaction_log (
    id BIGINT NOT NULL,
    conversation_id VARCHAR(255) NOT NULL,
    agente_id BIGINT,
    usuario_id VARCHAR(100),
    mensagem_usuario TEXT NOT NULL,
    mensagem_ia TEXT,
    modelo VARCHAR(100),
    tempo_resposta_ms BIGINT,
    tokens_usados INTEGER,
    custo DECIMAL(10, 4),
    status VARCHAR(50) NOT NULL,
    erro TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_ai_interaction_log_agente FOREIGN KEY (agente_id) REFERENCES llm_agente(id)
);

CREATE INDEX IF NOT EXISTS idx_llm_ai_interaction_log_conversation_id ON llm_ai_interaction_log(conversation_id);
CREATE INDEX IF NOT EXISTS idx_llm_ai_interaction_log_agente_id ON llm_ai_interaction_log(agente_id);
CREATE INDEX IF NOT EXISTS idx_llm_ai_interaction_log_usuario_id ON llm_ai_interaction_log(usuario_id);
CREATE INDEX IF NOT EXISTS idx_llm_ai_interaction_log_created_at ON llm_ai_interaction_log(created_at);
