package com.ia.core.service.dto.request;

/**
 * Tradutor de requisição
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class SearchRequestTranslator {
  public static final String FILTER_REQUEST_CLASS = SearchRequestDTO.class
      .getCanonicalName();
  public static final String FILTER_REQUEST = "search.request";

  /**
   * Campos de ajuda
   */
  public static final class HELP {
    public static final String FILTER_REQUEST = "search.request.help";

  }
}
