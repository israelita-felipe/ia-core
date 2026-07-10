package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.authorization.HasContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Gerenciador de funcionalidades do sistema.
 * <p>
 * Responsável por gerenciar o registro e recuperação de funcionalidades
 * associadas aos serviços que implementam interfaces com capacidades.
 *
 * @author Israel Araújo
 * @see Functionality
 * @since 1.0.0
 */
public interface FunctionalityManager {

  /**
   * Adiciona uma funcionalidade ao gerenciador.
   *
   * @param functionality a funcionalidade a ser adicionada
   */
  void addFunctionality(Functionality functionality);

  /**
   * Adiciona funcionalidade a partir de um serviço que a implementa.
   *
   * @param service o serviço com a funcionalidade
   * @return a funcionalidade adicionada
   */
  default Functionality addFunctionality(HasFunctionality service) {
    Set<String> context = new HashSet<>();
    if (HasContext.class.isInstance(service)) {
      context.addAll(((HasContext) service).getContexts());
    }
    return addFunctionality(service.getFunctionalityTypeName(),
                            PrivilegeType.SYSTEM, context);
  }

  /**
   * Cria e adiciona uma funcionalidade com os parâmetros especificados.
   *
   * @param name nome da funcionalidade
   * @param type tipo do privilégio
   * @param context conjunto de contextos
   * @return a funcionalidade criada e adicionada
   */
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

  /**
   * Retorna todas as funcionalidades registradas.
   *
   * @return conjunto de funcionalidades
   */
  Set<Functionality> getFunctionalities();

  /**
   * Combina funcionalidades de múltiplos fornecedores.
   *
   * @param suppliers fornecedores que fornecem conjuntos de funcionalidades
   * @return conjunto combinado de funcionalidades
   */
  public static Set<Functionality> combine(java.util.function.Supplier<Set<Functionality>>... suppliers) {
    Set<Functionality> result = new HashSet<>();
    for (java.util.function.Supplier<Set<Functionality>> supplier : suppliers) {
      result.addAll(supplier.get());
    }
    return result;
  }
}
