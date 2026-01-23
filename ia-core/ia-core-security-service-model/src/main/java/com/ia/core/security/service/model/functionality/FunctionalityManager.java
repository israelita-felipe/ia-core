package com.ia.core.security.service.model.functionality;

import java.util.HashSet;
import java.util.Set;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.authorization.HasContext;

/**
 * @author Israel Araújo
 */
public interface FunctionalityManager {
  void addFunctionality(Functionality functionality);

  default Functionality addFunctionality(HasFunctionality service) {
    Set<String> context = new HashSet<>();
    if (HasContext.class.isInstance(service)) {
      context.addAll(((HasContext) service).getContexts());
    }
    return addFunctionality(service.getFunctionalityTypeName(),
                            PrivilegeType.SYSTEM, context);
  }

  default Functionality addFunctionality(String name, PrivilegeType type,
                                         Set<String> context) {
    Functionality functionality = new Functionality() {

      @Override
      public Set<String> getValues() {
        return context;
      }

      @Override
      public PrivilegeType getType() {
        return type;
      }

      @Override
      public String getName() {
        return name;
      }
    };
    addFunctionality(functionality);
    return functionality;
  }

  Set<Functionality> getFunctionalities();

}
