package com.ia.core.llm.model.ferramenta;

/**
 * Enumeração que define o tipo de capacidade registrada em {@link Ferramenta}.
 * <p>
 * Define os tipos possíveis de ferramentas: agente especialista, tool Spring
 * ou recurso MCP.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum TipoFerramentaEnum {
  AGENTE_ESPECIALISTA,
  TOOL_SPRING,
  RECURSO_MCP,
  SKILL
}
