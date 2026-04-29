package com.ia.core.security.view.privilege.page;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.privilege.form.PrivilegeFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe que representa as configurações para privilege page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegePageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class PrivilegePageViewModelConfig
  extends EntityPageViewModelConfig<PrivilegeDTO> {
  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param service
   * @param logOperationService
   */

public PrivilegePageViewModelConfig(PrivilegeManager service,
                                      List<HasContext> hasContext,
                                      LogOperationManager logOperationService) {
    super(service, logOperationService);
    this.hasContext = hasContext;
  }

  @Override
  protected FormViewModelConfig<PrivilegeDTO> createFormViewModelConfig(boolean readOnly) {
    return new PrivilegeFormViewModelConfig(readOnly, this.hasContext);
  }
}
