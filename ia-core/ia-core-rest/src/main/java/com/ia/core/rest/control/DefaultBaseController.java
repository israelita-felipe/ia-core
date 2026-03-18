package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

/**
 * Classe padrão para criação de um controlador com todos os serviços.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 * @see AbstractBaseController
 * @see CountBaseController
 * @see FindBaseController
 */
public abstract class DefaultBaseController<T extends BaseEntity, D extends DTO<?>>
  extends AbstractBaseController<T, D>
  implements CountBaseController<T, D>, FindBaseController<T, D>,
  DeleteBaseController<T, D>, ListBaseController<T, D>,
  SaveBaseController<T, D> {

  /**
   * @param service
   */
  // TODO [P1] LINHA 25: Adicionar validação de entrada no construtor
  // Construtor não valida se service é nulo, violando fail-fast principle
   // Adicionar: Objects.requireNonNull(service, "Service não pode ser nulo")
  // Status: PENDENTE - Segurança: permite inicialização com dependência nula
  // TODO [P2] LINHA 16-17: Considerar segregação com padrão CQRS
  // Implementa 5 interfaces (CountBaseController, FindBaseController, DeleteBaseController, ListBaseController, SaveBaseController)
  // Para projetos maiores, considerar: PessoaQueryController, PessoaMutationController
  // Status: PENDENTE - Arquitetura: melhor separação de concerns (read vs write)
  public DefaultBaseController(BaseService<T, D> service) {
    super(service);
  }

}
