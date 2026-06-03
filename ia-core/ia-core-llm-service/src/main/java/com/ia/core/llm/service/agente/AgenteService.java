package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.model.agente.AgenteUseCase;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de agentes especialistas.
 * <p>
 * Implementa operações CRUD para agentes utilizados na orquestração multi-agente,
 * seguindo o padrão ia-core para integração com spring-ai-agent-utils.
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

  public AgenteService(AgenteServiceConfig config) {
    super(config);
    this.agenteRepository = (AgenteRepository) config.getRepository();
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

    // Salvar relacionamento com ferramentas
    if (dto.getFerramentas() != null && !dto.getFerramentas().isEmpty()) {
      Agente agente = agenteRepository.findById(saved.getId())
          .orElseThrow(() -> new ServiceException("Agente não encontrado após save"));
      List<Ferramenta> ferramentas = resolveFerramentas(dto.getFerramentas());
      agente.setFerramentas(ferramentas);
      log.debug("Adicionadas {} ferramentas ao agente {}", ferramentas.size(), agente.getIdentificador());
    }

    // Salvar relacionamento com skills
    if (dto.getSkills() != null && !dto.getSkills().isEmpty()) {
      Agente agente = agenteRepository.findById(saved.getId())
          .orElseThrow(() -> new ServiceException("Agente não encontrado após save"));
      List<Skill> skills = resolveSkills(dto.getSkills());
      agente.setSkills(skills);
      log.debug("Adicionadas {} skills ao agente {}", skills.size(), agente.getIdentificador());
    }

    Agente agente = agenteRepository.findById(saved.getId())
        .orElseThrow(() -> new ServiceException("Agente não encontrado após save"));
    return getMapper().toDTO(agente);
  }

  /**
   * Resolve as ferramentas a partir dos DTOs.
   *
   * @param ferramentaDtos lista de DTOs de ferramenta
   * @return lista de entidades Ferramenta
   */
  private List<Ferramenta> resolveFerramentas(List<com.ia.core.llm.service.model.ferramenta.FerramentaDTO> ferramentaDtos) {
    List<Ferramenta> result = new ArrayList<>();
    for (com.ia.core.llm.service.model.ferramenta.FerramentaDTO f : ferramentaDtos) {
      if (f.getId() != null) {
        getConfig().getFerramentaRepository().findById(f.getId()).ifPresent(result::add);
      } else if (f.getIdentificador() != null) {
        getConfig().getFerramentaRepository().findByIdentificador(f.getIdentificador()).ifPresent(result::add);
      }
    }
    return result;
  }

  /**
   * Resolve as skills a partir dos DTOs.
   *
   * @param skillDtos lista de DTOs de skill
   * @return lista de entidades Skill
   */
  private List<Skill> resolveSkills(List<com.ia.core.llm.service.model.skill.SkillDTO> skillDtos) {
    List<Skill> result = new ArrayList<>();
    for (com.ia.core.llm.service.model.skill.SkillDTO s : skillDtos) {
      if (s.getId() != null) {
        getConfig().getSkillRepository().findById(s.getId()).ifPresent(result::add);
      }
    }
    return result;
  }

  @Override
  public AgenteServiceConfig getConfig() {
    return (AgenteServiceConfig) super.getConfig();
  }
}
