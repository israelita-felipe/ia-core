package com.ia.core.view.translator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.ia.core.service.translator.Translator;
import com.vaadin.flow.i18n.I18NProvider;

/**
 * @author Israel Araújo
 */
public class CoreViewTranslator
  implements Translator, I18NProvider {

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
   * Construtor padrão
   *
   * @param prefix Prefixos utilizados para captura dos bundles
   */
  public CoreViewTranslator(String... prefix) {
    messageSource = new ResourceBundleMessageSource();
    messageSource.addBasenames(prefix);
  }

  /**
   * @return {@link #locales} contendo por padrão {@link #LOCALE_PT_BR}
   */
  @Override
  public List<Locale> getProvidedLocales() {
    return locales;
  }

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
