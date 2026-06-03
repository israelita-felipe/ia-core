-- Migration: Unifica conceitos Skill e Ferramenta em um único conceito (Ferramenta)
-- Skills passam a ser Ferramentas com tipo=SKILL

-- 1. Adicionar colunas do Skill à tabela LLM_FERRAMENTA
ALTER TABLE LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA ADD COLUMN instrucoes CLOB;
ALTER TABLE LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA ADD COLUMN template_id BIGINT;
ALTER TABLE LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA ADD CONSTRAINT fk_llm_ferramenta_template
  FOREIGN KEY (template_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_TEMPLATE(id);

-- 2. Criar tabela de sub-ferramentas (auto-relacionamento ManyToMany)
CREATE TABLE IF NOT EXISTS LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA_SUB_FERRAMENTA (
  ferramenta_id BIGINT NOT NULL,
  sub_ferramenta_id BIGINT NOT NULL,
  PRIMARY KEY (ferramenta_id, sub_ferramenta_id),
  CONSTRAINT fk_ferramenta_sub_parent FOREIGN KEY (ferramenta_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA(id),
  CONSTRAINT fk_ferramenta_sub_child FOREIGN KEY (sub_ferramenta_id) REFERENCES LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA(id)
);

-- 3. Migrar skills existentes para ferramentas com tipo=SKILL
INSERT INTO LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA (id, version, created_at, updated_at, created_by, updated_by, titulo, descricao, tipo, identificador, ativo, descoberta_automatica, instrucoes, template_id)
SELECT s.id + 10000, s.version, s.created_at, s.updated_at, s.created_by, s.updated_by,
       s.titulo, s.descricao, 'SKILL', CONCAT('skill.', CAST(s.id AS VARCHAR(20))), s.ativo, FALSE,
       s.instrucoes, s.template_id
FROM LARGE_LANGUAGE_MODEL.LLM_SKILL s
WHERE NOT EXISTS (SELECT 1 FROM LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA f WHERE f.titulo = s.titulo);

-- 4. Migrar relacionamento skill→ferramenta para sub-ferramentas
INSERT INTO LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA_SUB_FERRAMENTA (ferramenta_id, sub_ferramenta_id)
SELECT sf.skill_id + 10000, sf.ferramenta_id
FROM LARGE_LANGUAGE_MODEL.LLM_SKILL_FERRAMENTA sf
WHERE EXISTS (SELECT 1 FROM LARGE_LANGUAGE_MODEL.LLM_FERRAMENTA f WHERE f.id = sf.skill_id + 10000);

-- 5. Migrar relacionamento agente→skill para agente→ferramenta
INSERT INTO LARGE_LANGUAGE_MODEL.LLM_AGENTE_FERRAMENTA (agente_id, ferramenta_id)
SELECT ags.agente_id, ags.skill_id + 10000
FROM LARGE_LANGUAGE_MODEL.LLM_AGENTE_SKILL ags
WHERE NOT EXISTS (
  SELECT 1 FROM LARGE_LANGUAGE_MODEL.LLM_AGENTE_FERRAMENTA af
  WHERE af.agente_id = ags.agente_id AND af.ferramenta_id = ags.skill_id + 10000
);

-- 6. Renomear coluna skill_id para ferramenta_id na tabela de auditoria
ALTER TABLE LARGE_LANGUAGE_MODEL.LLM_AI_INTERACTION_LOG ADD COLUMN ferramenta_id BIGINT;
UPDATE LARGE_LANGUAGE_MODEL.LLM_AI_INTERACTION_LOG SET ferramenta_id = skill_id WHERE skill_id IS NOT NULL;
ALTER TABLE LARGE_LANGUAGE_MODEL.LLM_AI_INTERACTION_LOG DROP COLUMN skill_id;

-- 7. Remover tabelas do conceito Skill (não mais necessárias)
DROP TABLE IF EXISTS LARGE_LANGUAGE_MODEL.LLM_AGENTE_SKILL;
DROP TABLE IF EXISTS LARGE_LANGUAGE_MODEL.LLM_SKILL_FERRAMENTA;
DROP TABLE IF EXISTS LARGE_LANGUAGE_MODEL.LLM_SKILL;
