package com.ia.core.service.translator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author Israel Araújo
 */
public class CoreTranslator
  implements Translator {

  /** Serial UID */
  private static final long serialVersionUID = -6976279851016981778L;

  /**
   * Prefixo
   */
  public static final String BUNDLE_PREFIX = "i18n/translations";

  /**
   * Local padrão
   */
  public final Locale LOCALE_PT_BR = Locale.of("pt", "BR");
  /** Bundle padrão */
  private final ResourceBundleMessageSource messageSource;

  /**
   * Locais disponíveis
   */
  private List<Locale> locales = Collections
      .unmodifiableList(Arrays.asList(LOCALE_PT_BR));

  /**
   * @param prefix Prefixos dos arquivos de tradução que devem ser carregados
   *               neste tradutor.
   */
  public CoreTranslator(String... prefix) {
    messageSource = new ResourceBundleMessageSource();
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.addBasenames(prefix);
  }

  /**
   * @return Lista de locais suportados para tradução
   */
  @Override
  public List<Locale> getProvidedLocales() {
    return locales;
  }

  /**
   * Captura uma tradução
   *
   * @param key    Chave
   * @param locale {@link Locale}
   * @param params Parâmetros
   * @return Tradução, ou a própria key se não for possível encontrar tradução.
   */
  @Override
  public String getTranslation(String key, Locale locale,
                               Object... params) {
    if (key == null) {
      return "";
    }

    String value;
    try {
      value = messageSource.getMessage(key, params, locale);
    } catch (final NoSuchMessageException e) {
      return key;
    }
    return value;
  }
}
