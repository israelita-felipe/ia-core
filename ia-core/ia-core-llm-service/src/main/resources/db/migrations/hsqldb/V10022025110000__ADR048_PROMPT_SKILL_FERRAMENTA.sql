CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_PROMPT (
  id BIGINT NOT NULL PRIMARY KEY,
  version BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255),
  finalidade VARCHAR(50),
  titulo VARCHAR(200) NOT NULL,
  entrada VARCHAR(500),
  template_id BIGINT NOT NULL,
  CONSTRAINT fk_llm_prompt_template FOREIGN KEY (template_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_TEMPLATE(id)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA (
  id BIGINT NOT NULL PRIMARY KEY,
  version BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255),
  titulo VARCHAR(200) NOT NULL,
  descricao VARCHAR(1000),
  tipo VARCHAR(50) NOT NULL,
  identificador VARCHAR(255) NOT NULL,
  modulo_origem VARCHAR(255),
  ativo BOOLEAN DEFAULT TRUE,
  descoberta_automatica BOOLEAN DEFAULT FALSE,
  CONSTRAINT uq_llm_ferramenta_titulo UNIQUE (titulo),
  CONSTRAINT uq_llm_ferramenta_identificador UNIQUE (identificador)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_SKILL (
  id BIGINT NOT NULL PRIMARY KEY,
  version BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255),
  titulo VARCHAR(200) NOT NULL,
  descricao VARCHAR(1000),
  instrucoes CLOB,
  template_id BIGINT,
  ativo BOOLEAN DEFAULT TRUE,
  CONSTRAINT uq_llm_skill_titulo UNIQUE (titulo),
  CONSTRAINT fk_llm_skill_template FOREIGN KEY (template_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_TEMPLATE(id)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_SKILL_FERRAMENTA (
  skill_id BIGINT NOT NULL,
  ferramenta_id BIGINT NOT NULL,
  PRIMARY KEY (skill_id, ferramenta_id),
  CONSTRAINT fk_skill_ferramenta_skill FOREIGN KEY (skill_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_SKILL(id),
  CONSTRAINT fk_skill_ferramenta_ferramenta FOREIGN KEY (ferramenta_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA(id)
);

CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_AI_INTERACTION_LOG (
  id BIGINT NOT NULL PRIMARY KEY,
  version BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255),
  correlation_id VARCHAR(64),
  usuario_id VARCHAR(255),
  user_prompt CLOB,
  tool_calls CLOB,
  llm_reasoning CLOB,
  resposta_final CLOB,
  skill_id BIGINT
);
