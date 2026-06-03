package com.ia.core.llm.view.agente.page;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.view.agente.AgenteManager;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Classe que representa as configurações para agente page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgentePageViewModelConfig
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@UIScope
@Component
public class AgentePageViewModelConfig
  extends EntityPageViewModelConfig<AgenteDTO> {

  /**
   * @param service serviço de agentes
   * @param logOperationService serviço de log de operações
   */
  public AgentePageViewModelConfig(AgenteManager service,
                                    LogOperationManager logOperationService) {
    super(service, logOperationService);
    log.debug("AgentePageViewModelConfig inicializado");
  }
}
