package com.ia.core.flyway.view.flywayexecution.page;

import org.springframework.stereotype.Component;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuração do ViewModel para a página de execuções do Flyway.
 *
 * @author Israel Araújo
 */
@Slf4j
@Getter
@Component
public class FlywayExecutionPageViewModelConfig
  extends PageViewModelConfig<FlywayExecutionDTO> {
  /**
   * @param service
   */
  public FlywayExecutionPageViewModelConfig(DefaultBaseManager<FlywayExecutionDTO> service) {
    super(service);
  }

}
