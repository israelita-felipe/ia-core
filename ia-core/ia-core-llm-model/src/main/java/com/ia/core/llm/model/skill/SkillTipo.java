package com.ia.core.llm.model.skill;

/**
 * Tipo de skill.
 * <p>
 * Define o tipo de habilidade especializada que uma skill representa.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum SkillTipo {
  /**
   * Skill para construção de ontologias.
   */
  ONTOLOGY_BUILDER,

  /**
   * Skill para extração de conhecimento.
   */
  KNOWLEDGE_EXTRACTION,

  /**
   * Skill para raciocínio.
   */
  REASONING,

  /**
   * Tipo genérico para finalidades customizadas.
   */
  OUTRA
}
