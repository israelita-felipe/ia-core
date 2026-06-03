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
  /**
   * Agente especialista - um agente de IA com conhecimento específico em um domínio.
   */
  AGENTE_ESPECIALISTA("Agente Especialista", "Agente de IA com conhecimento específico em um domínio"),

  /**
   * Tool Spring - ferramenta implementada como componente Spring com anotação @Tool.
   */
  TOOL_SPRING("Tool Spring", "Ferramenta implementada como componente Spring com anotação @Tool",false),

  /**
   * Recurso MCP - ferramenta exposta através do protocolo Model Context Protocol.
   */
  RECURSO_MCP("Recurso MCP", "Ferramenta exposta através do protocolo Model Context Protocol",false);

  private final String displayName;
  private final String description;
  private final boolean editAllowed;

  TipoFerramentaEnum(String displayName, String description) {
    this(displayName,description,true);
  }
TipoFerramentaEnum(String displayName,String description, boolean editAllowed){
    this.displayName = displayName;
    this.description = description;
    this.editAllowed = editAllowed;
}
  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public boolean isEditAllowed() {
    return editAllowed;
  }
}
