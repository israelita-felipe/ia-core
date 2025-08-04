package com.ia.core.service.translator;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Tradutor da aplicação
 *
 * @author Israel Araújo
 */
public interface Translator
  extends Serializable {
  /** Local padrão 'pt_BR' */
  Locale DEFAULT_LOCALE = Locale.of("pt", "BR");

  /**
   * Captura o local padrão
   *
   * @return {@link #DEFAULT_LOCALE}
   */
  default Locale getLocale() {
    return DEFAULT_LOCALE;
  }

  /**
   * @return coleção de locais suportados
   */
  List<Locale> getProvidedLocales();

  /**
   * Captura uma tradução
   *
   * @param key    Chave da tradução
   * @param locale Local da tradução
   * @param params Parâmetros da tradução
   * @return A tradução encontrada
   */
  String getTranslation(String key, Locale locale, Object... params);

  /**
   * Captura uma tradução
   *
   * @param key    Chave
   * @param params Parâmetros de tradução
   * @return A tradução encontrada
   */
  default String getTranslation(String key, Object... params) {
    return getTranslation(key, getLocale(), params);
  }

}
