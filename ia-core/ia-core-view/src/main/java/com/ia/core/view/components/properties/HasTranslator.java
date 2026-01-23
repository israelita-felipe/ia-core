package com.ia.core.view.components.properties;

import com.ia.core.service.translator.Translator;

/**
 * Representa a capacidade de possuir um tradutor
 *
 * @author Israel Araújo
 */
public interface HasTranslator
  extends HasLocale {

  /**
   * Traduz uma classe
   *
   * @param type {@link Class} do tipo
   * @return {@link String} contendo a tradução
   */
  default String $(Class<?> type) {
    return $(type.getCanonicalName());
  }

  /**
   * Traduz uma mensagem.
   *
   * @param message Mensagem a ser traduzida.
   * @return String de tradução.
   */
  default String $(String message) {
    return $(message, new Object[0]);
  }

  /**
   * Traduz um enum pelo nome
   *
   * @param enm nome
   * @return tradução do nome do enum
   */
  default String $(Enum<?> enm) {
    return $(enm.name());
  }

  /**
   * Traduz uma mensagem com parâmetros.
   *
   * @param message    Mensagem a ser traduzida.
   * @param parameters Parâmetros.
   * @return Mensagem traduzida.
   */
  default String $(String message, Object... parameters) {
    return getI18NProvider().getTranslation(message, getLocale(),
                                            parameters);
  }

  /**
   * @return {@link Translator} ativo
   */
  default Translator getTranslator() {
    if (Translator.class.isInstance(getI18NProvider())) {
      return (Translator) getI18NProvider();
    }
    throw new IllegalStateException("Tradutor não encontrado");
  }
}
