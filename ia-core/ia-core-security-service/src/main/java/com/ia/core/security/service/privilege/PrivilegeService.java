package com.ia.core.security.service.privilege;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.CrudSecuredBaseService;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.service.model.privilege.PrivilegeUseCase;
import com.ia.core.service.annotations.TransactionalReadOnly;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Serviço de negócio para gerenciamento de privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeService
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Service
public class PrivilegeService
  extends CrudSecuredBaseService<Privilege, PrivilegeDTO>
  implements PrivilegeUseCase {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public PrivilegeService(PrivilegeServiceConfig config) {
    super(Objects.requireNonNull(config, "config não pode ser null"));
  }

  /**
   * Captura todos os privilégios sem checagem de segurança
   *
   * @return lista de {@link PrivilegeDTO}
   */
  @TransactionalReadOnly
  @Tool(description = "Lista todos os privilégios do sistema sem verificação de segurança. " +
                     "Retorna uma lista completa de PrivilegeDTO contendo: identificador, nome, descrição " +
                     "e operações associadas. Não aplica filtros de autorização, sendo usado principalmente " +
                     "para inicialização do sistema ou operações administrativas. Útil para agentes de IA " +
                     "obterem o catálogo completo de privilégios disponíveis no sistema.")
  public List<PrivilegeDTO> findAll() {
    return getRepository().findAll().stream().map(this::toDTO).toList();
  }

  /**
   * Verifica se existe o privilégio pelo nome
   *
   * @param name nome do privilégio
   * @return <code>true</code> caso exista privilégio com mesmo nome.
   */
  @Tool(description = "Verifica se existe um privilégio com o nome especificado no sistema. " +
                     "Retorna true se já existir um privilégio com o mesmo nome (case-sensitive), " +
                     "false caso contrário. Útil para validação antes de criar novos privilégios " +
                     "para evitar duplicidade de nomes.")
  public boolean exitsByName(
          @ToolParam(description = "Nome do privilégio a verificar (String, obrigatório). " +
                                   "Case-sensitive. Deve corresponder exatamente ao nome do privilégio existente.",
                      required = true) String name) {
    Objects.requireNonNull(name, "name não pode ser null");
    return getRepository().existsByName(name);
  }

  @Override
  public PrivilegeServiceConfig getConfig() {
    return (PrivilegeServiceConfig) super.getConfig();
  }

  @Override
  public String getFunctionalityTypeName() {
    return PrivilegeTranslator.PRIVILEGE;
  }

  @Override
  public PrivilegeRepository getRepository() {
    return (PrivilegeRepository) super.getRepository();
  }
}
