package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.model.agente.AgenteUseCase;
import com.ia.core.llm.service.resolver.FerramentaResolver;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de agentes especialistas.
 * <p>
 * Implementa operações CRUD para agentes utilizados na orquestração multi-agente.
 * Ferramentas (incluindo skills de tipo=SKILL) são resolvidas via FerramentaResolver.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class AgenteService
  extends CrudBaseService<Agente, AgenteDTO>
  implements AgenteUseCase {

  private final AgenteRepository agenteRepository;
  private final FerramentaResolver ferramentaResolver;

  public AgenteService(AgenteServiceConfig config,
                       AgenteRepository agenteRepository,
                       FerramentaResolver ferramentaResolver) {
    super(config);
    this.agenteRepository = agenteRepository;
    this.ferramentaResolver = ferramentaResolver;
  }

  @Override
  @TransactionalReadOnly
  public Optional<AgenteDTO> findByIdentificador(String identificador) {
    log.debug("Buscando agente por identificador: {}", identificador);
    return agenteRepository.findByIdentificador(identificador)
        .map(agente -> {
          log.debug("Agente encontrado: {}", agente.getTitulo());
          return getMapper().toDTO(agente);
        });
  }

  @Override
  @TransactionalReadOnly
  public List<AgenteDTO> listAtivos() {
    log.debug("Listando agentes ativos");
    return agenteRepository.findByAtivoTrue().stream()
        .map(agente -> {
          log.debug("Agente ativo encontrado: {}", agente.getTitulo());
          return getMapper().toDTO(agente);
        })
        .collect(Collectors.toList());
  }

  @Override
  @TransactionalWrite
  public AgenteDTO save(AgenteDTO dto) throws ServiceException {
    log.debug("Salvando agente: identificador={}, titulo={}", dto.getIdentificador(), dto.getTitulo());
    AgenteDTO saved = super.save(dto);
    Agente agente = agenteRepository.findById(saved.getId())
        .orElseThrow(() -> new ServiceException("Agente não encontrado após save"));

    List<Ferramenta> ferramentas = ferramentaResolver.resolve(dto.getFerramentas());
    agente.setFerramentas(ferramentas);

    agenteRepository.save(agente);
    return getMapper().toDTO(agente);
  }
}
