package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.authorization.HasContext;

import java.util.HashSet;
import java.util.Set;
/**
 * Gerenciador de functionality.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a FunctionalityManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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

        /**
         * Combines functionalities from multiple suppliers.
         *
         * @param suppliers stream of suppliers providing sets of functionalities
         * @return combined set of functionalities
         */


    public static Set<Functionality> combine(java.util.function.Supplier<Set<Functionality>>... suppliers) {
        Set<Functionality> result = new HashSet<>();
        for (java.util.function.Supplier<Set<Functionality>> supplier : suppliers) {
            result.addAll(supplier.get());
        }
        return result;
    }
}
