package com.ia.core.llm.service.model.skill;

public final class SkillTranslator {

  private SkillTranslator() {}

  public static final String SKILL = "skill";
  public static final String TITULO = "skill.titulo";
  public static final String DESCRICAO = "skill.descricao";
  public static final String INSTRUCOES = "skill.instrucoes";
  public static final String FERRAMENTAS = "skill.ferramentas";
  public static final String ATIVO = "skill.ativo";

  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "validation.skill.titulo.required";
  }
}
