package com.ia.core.llm.service.model.comando;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class ComandoSistemaTranslator {
  public static final class HELP {
    public static final String COMANDO_SISTEMA = "comando.sistema.help";
    public static final String TITULO = "comando.sistema.help.titulo";
    public static final String COMANDO = "comando.sistema.help.comando";
    public static final String FINALIDADE = "comando.sistema.help.finalidade";
  }
  public static final String COMANDO_SISTEMA_CLASS = ComandoSistemaDTO.class
      .getCanonicalName();
  public static final String COMANDO_SISTEMA = "comando.sistema";
  public static final String TITULO = "comando.sistema.titulo";
  public static final String COMANDO = "comando.sistema.comando";

  public static final String FINALIDADE = "comando.sistema.finalidade";
}
