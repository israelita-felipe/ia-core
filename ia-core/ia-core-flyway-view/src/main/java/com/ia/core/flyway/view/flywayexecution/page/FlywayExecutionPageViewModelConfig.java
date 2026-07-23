package com.ia.core.flyway.view.flywayexecution.page;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Configuração do ViewModel para a página de execuções do Flyway.
 * <p>
 * Responsável por fornecer as dependências necessárias para o ViewModel da página.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Getter
@Component
public class FlywayExecutionPageViewModelConfig<T extends FlywayExecutionDTO<?>>
  extends PageViewModelConfig<T> {
  /**
   * @param service
   */
  public FlywayExecutionPageViewModelConfig(DefaultBaseManager<T> service) {
    super(service);
  }

}
