-- =============================================================================
-- FASE 4: Performance e Otimização
-- Índices para otimização de queries e prevenção de N+1
-- =============================================================================
-- Schema: LLM
-- Tabelas: LLM_COMANDO_SISTEMA, LLM_TEMPLATE, LLM_TEMPLATE_PARAMETER
-- =============================================================================

-- -----------------------------------------------------------------------------
-- Índices para tb_comando_sistema
-- -----------------------------------------------------------------------------

-- Índice para busca por finalidade (frequente em filtros)
CREATE INDEX idx_llm_comando_sistema_finalidade 
ON LLM_COMANDO_SISTEMA(finalidade);

-- Índice único para título (já existe unique constraint, mas acelera buscas)
CREATE INDEX idx_llm_comando_sistema_titulo 
ON LLM_COMANDO_SISTEMA(titulo);

-- Índice para chave estrangeira template
CREATE INDEX idx_llm_comando_sistema_template 
ON LLM_COMANDO_SISTEMA(template);

-- Índice composto para filtros comuns (finalidade + criado_em)
CREATE INDEX idx_llm_comando_sistema_finalidade_criado 
ON LLM_COMANDO_SISTEMA(finalidade, criado_em DESC);

-- -----------------------------------------------------------------------------
-- Índices para tb_template
-- -----------------------------------------------------------------------------

-- Índice para título (frequente em buscas)
CREATE INDEX idx_llm_template_titulo 
ON LLM_TEMPLATE(titulo);

-- Índice para flag de contexto (filtros frequentes)
CREATE INDEX idx_llm_template_exige_contexto 
ON LLM_TEMPLATE(flg_exige_contexto);

-- -----------------------------------------------------------------------------
-- Índices para tb_template_parameter
-- -----------------------------------------------------------------------------

-- Índice para chave estrangeira template (N+1 prevention)
CREATE INDEX idx_llm_template_parameter_template 
ON LLM_TEMPLATE_PARAMETER(template);

-- Índice para nome do parâmetro (frequente em validações)
CREATE INDEX idx_llm_template_parameter_nome 
ON LLM_TEMPLATE_PARAMETER(nome_parametro);

-- -----------------------------------------------------------------------------
-- Índices para auditoria (tabelas BaseEntity)
-- -----------------------------------------------------------------------------

-- Índices para campos de auditoria (usados em quase todas as queries)
CREATE INDEX idx_llm_baseentity_criado_em 
ON LLM_COMANDO_SISTEMA(criado_em);
CREATE INDEX idx_llm_baseentity_criado_em 
ON LLM_TEMPLATE(criado_em);
CREATE INDEX idx_llm_baseentity_criado_em 
ON LLM_TEMPLATE_PARAMETER(criado_em);

-- =============================================================================
-- Comentários sobre os índices
-- =============================================================================
-- 
-- N+1 Query Prevention:
--   - idx_llm_comando_sistema_template: evita N+1 ao carregar template
--   - idx_llm_template_parameter_template: evita N+1 ao carregar parâmetros
-- 
-- Frequent Query Optimization:
--   - idx_llm_comando_sistema_finalidade: filtros por finalidade
--   - idx_llm_comando_sistema_finalidade_criado: ordenação padrão
--   - idx_llm_template_exige_contexto: filtros de contexto
-- 
-- Uniqueness:
--   - idx_llm_comando_sistema_titulo: unique constraint implícito
--   - idx_llm_template_titulo: unique constraint implícito
-- =============================================================================
