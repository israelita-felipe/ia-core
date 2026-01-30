package com.ia.core.service.contract;

import com.ia.core.service.translator.Translator;

/**
 * Interface segregada para acesso ao tradutor.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao tradutor.
 *
 * @author Israel Araújo
 */
public interface HasTranslator {

  /**
   * Obtém o tradutor padrão do serviço.
   *
   * @return Tradutor do serviço
   */
  Translator getTranslator();
}
