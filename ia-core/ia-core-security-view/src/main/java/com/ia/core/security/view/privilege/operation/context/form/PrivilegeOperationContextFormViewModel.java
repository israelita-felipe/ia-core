package com.ia.core.security.view.privilege.operation.context.form;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * View Model formulário de usuário
 *
 * @author Israel Araújo
 */
public class PrivilegeOperationContextFormViewModel
  extends FormViewModel<PrivilegeOperationContextDTO> {

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeManager}
   * @param roleService      {@link RoleManager}
   */
  public PrivilegeOperationContextFormViewModel(PrivilegeOperationContextFormViewModelConfig config) {
    super(config);
  }

  @Override
  public PrivilegeOperationContextFormViewModelConfig getConfig() {
    return (PrivilegeOperationContextFormViewModelConfig) super.getConfig();
  }

  public Collection<String> getContextKeys() {
    PrivilegeDTO privilege = getConfig().getPrivilege().get();
    if (privilege == null) {
      return Collections.emptyList();
    }
    return privilege.getValues();
  }

  /**
   *
   */
  public String getPrivilegeName() {
    PrivilegeDTO privilege = getConfig().getPrivilege().get();
    if (privilege == null) {
      return null;
    }
    return privilege.getName();
  }

  public Collection<ContextDefinition> getContextValues() {
    String contextKey = getModel().getContextKey();
    if (contextKey == null) {
      return Collections.emptySet();
    }
    return getConfig().getHasContext().stream()
        .filter(hasContext -> Objects.equals(hasContext.getContextName(),
                                             getPrivilegeName()))
        .flatMap(hasContext -> hasContext.getContextDefinition(contextKey)
            .get().stream())
        .collect(Collectors.toSet());
  }
}
