package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.model.agente.AgenteUseCase;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de agentes especialistas.
 * <p>
 * Implementa operações CRUD para agentes utilizados na orquestração multi-agente.
 * Ferramentas (incluindo skills) são convertidas automaticamente pelo AgenteMapper.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class AgenteService
  extends CrudBaseService<Agente, AgenteDTO>
  implements AgenteUseCase {

  public AgenteService(AgenteServiceConfig config) {
    super(config);
  }

  @Override
  public AgenteRepository getRepository() {
    return (AgenteRepository) super.getRepository();
  }

  @Override
  public AgenteMapper getMapper() {
    return (AgenteMapper) super.getMapper();
  }

  @Override
  @TransactionalReadOnly
  public Optional<AgenteDTO> findByIdentificador(String identificador) {
    log.debug("Buscando agente por identificador: {}", identificador);
    var repository = getRepository();
    if (repository == null) {
      return Optional.empty();
    }
    var mapper = getMapper();
    return repository.findByIdentificador(identificador)
        .map(agente -> {
          log.debug("Agente encontrado: {}", agente.getTitulo());
          return mapper != null ? mapper.toDTO(agente) : null;
        });
  }

  @Override
  @TransactionalReadOnly
  public List<AgenteDTO> listAtivos() {
    log.debug("Listando agentes ativos");
    var repository = getRepository();
    if (repository == null) {
      return List.of();
    }
    var mapper = getMapper();
    return repository.findByAtivoTrue().stream()
        .map(agente -> {
          log.debug("Agente ativo encontrado: {}", agente.getTitulo());
          return mapper != null ? mapper.toDTO(agente) : null;
        })
        .filter(dto -> dto != null)
        .collect(Collectors.toList());
  }
}
