package com.ia.core.security.service.model.functionality;

import java.util.Set;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.Operation;

/**
 * @author Israel Araújo
 */
public interface FunctionalityManager {
  void addFunctionality(Functionality functionality);

  default Functionality addFunctionality(HasFunctionality service,
                                         Operation operation) {
    return addFunctionality(service.getFunctionalityTypeName(), operation);
  }

  default Functionality addFunctionality(String name, Operation operation) {
    Functionality functionality = () -> operation.create(name);
    addFunctionality(functionality);
    return functionality;
  }

  Set<Functionality> getFunctionalities();

}
