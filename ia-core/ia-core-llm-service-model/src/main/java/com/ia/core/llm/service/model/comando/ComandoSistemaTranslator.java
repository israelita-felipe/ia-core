package com.ia.core.llm.service.model.comando;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio comando sistema translator.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistemaTranslator
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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

  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "validation.comando.titulo.required";
    public static final String TITULO_SIZE = "validation.comando.titulo.size";
    public static final String COMANDO_SIZE = "validation.comando.comando.size";
    public static final String TEMPLATE_REQUIRED = "validation.comando.template.required";
    public static final String FINALIDADE_REQUIRED = "validation.comando.finalidade.required";
  }
}
