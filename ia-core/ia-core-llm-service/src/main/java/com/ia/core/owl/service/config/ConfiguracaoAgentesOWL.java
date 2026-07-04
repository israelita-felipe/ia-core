package com.ia.core.owl.service.config;

import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.validation.ExplicadorInconsistencia;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração Spring para agentes guiados por ontologias.
 * <p>
 * Segue o padrão ADR-004 para injeção de dependências via ServiceConfig.
 * As tools OWL são registradas automaticamente via @Component nos pacotes
 * com.ia.core.owl.service.tool.* (classexpression, dataproperty, individual, objectproperty, annotation).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ConfiguracaoAgentesOWL {

  /**
   * Cria explicador de inconsistências.
   *
   * @return explicador de inconsistências
   */
  @Bean
  public ExplicadorInconsistencia explicadorInconsistencia() {
    log.info("Configurando ExplicadorInconsistencia");
    return new ExplicadorInconsistencia();
  }

  /**
   * Cria loop LLM-Reasoner para auto-correção.
   *
   * @param llmCommunicator comunicador LLM
   * @param owlService serviço OWL para validação
   * @param explicador explicador de inconsistências
   * @return loop LLM-Reasoner
   */
  @Bean
  public LoopLLMRaciocinador loopLLMRaciocinador(
                                                 LLMCommunicator llmCommunicator,
                                                 DefaultOwlService owlService,
                                                 ExplicadorInconsistencia explicador) {
    log.info("Configurando LoopLLMRaciocinador");
    return new LoopLLMRaciocinador(llmCommunicator, owlService, explicador);
  }
}
