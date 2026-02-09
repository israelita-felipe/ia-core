package com.ia.core.owl.service.model.axioma;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class AxiomaTranslator {
  public static final class HELP {
    public static final String URI = "axioma.help.uri";
    public static final String URI_VERSION = "axioma.help.uri.version";
    public static final String PREFIX = "axioma.help.prefix";
    public static final String EXPRESSAO = "axioma.help.expressao";
    public static final String IS_CONSISTENTE = "axioma.help.consistente";
    public static final String IS_INFERIDO = "axioma.help.inferido";
    public static final String IS_ATIVO = "axioma.help.ativo";
  }

  public static final String AXIOMA_CLASS = AxiomaDTO.class
      .getCanonicalName();

  public static final String URI = "axioma.uri";
  public static final String URI_VERSION = "axioma.uri.version";
  public static final String PREFIX = "axioma.prefix";
  public static final String EXPRESSAO = "axioma.expressao";
  public static final String IS_CONSISTENTE = "axioma.consistente";
  public static final String IS_INFERIDO = "axioma.inferido";
  public static final String IS_ATIVO = "axioma.ativo";

  public static final class VALIDATION {
    public static final String PREFIX_REQUIRED = "validation.axioma.prefix.required";
    public static final String PREFIX_SIZE = "validation.axioma.prefix.size";
    public static final String URI_VERSION_REQUIRED = "validation.axioma.uriVersion.required";
    public static final String EXPRESSAO_REQUIRED = "validation.axioma.expressao.required";
    public static final String EXPRESSAO_SIZE = "validation.axioma.expressao.size";
  }
}
