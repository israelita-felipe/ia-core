package com.ia.core.llm.service.skill;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.skill.SkillActivationDTO;
import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
import com.ia.core.llm.service.model.skill.SkillUseCase;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de skills.
 * <p>
 * Implementa operações CRUD para skills utilizadas por agentes de IA,
 * incluindo carregamento para ativação e listagem de metadados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class SkillService
  extends CrudBaseService<Skill, SkillDTO>
  implements SkillUseCase {

  private final SkillRepository skillRepository;

  public SkillService(SkillServiceConfig config) {
    super(config);
    this.skillRepository = (SkillRepository) config.getRepository();
  }

  @Override
  @TransactionalReadOnly
  public List<SkillMetadataDTO> listMetadata() {
    return skillRepository.findByAtivoTrue().stream()
        .map(skill -> SkillMetadataDTO.builder()
            .id(skill.getId())
            .titulo(skill.getTitulo())
            .descricao(skill.getDescricao())
            .ferramentaCount(skill.getFerramentas() == null ? 0 : skill.getFerramentas().size())
            .ativo(skill.isAtivo())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  @TransactionalReadOnly
  public SkillActivationDTO loadForActivation(Long id) {
    Skill skill = skillRepository.findById(id)
        .orElseThrow(() -> new ServiceException("Skill não encontrada: " + id));
    SkillDTO dto = getMapper().toDTO(skill);
    return SkillActivationDTO.builder()
        .id(dto.getId())
        .titulo(dto.getTitulo())
        .descricao(dto.getDescricao())
        .instrucoes(skill.getInstrucoes())
        .template(dto.getTemplate())
        .ferramentas(dto.getFerramentas() == null ? new ArrayList<>() : dto.getFerramentas())
        .build();
  }

  @Override
  @TransactionalWrite
  public SkillDTO save(SkillDTO dto) throws ServiceException {
    SkillDTO saved = super.save(dto);
    if (dto.getFerramentas() != null && !dto.getFerramentas().isEmpty()) {
      Skill skill = skillRepository.findById(saved.getId())
          .orElseThrow(() -> new ServiceException("Skill não encontrada após save"));
      List<Ferramenta> ferramentas = resolveFerramentas(dto.getFerramentas());
      skill.setFerramentas(ferramentas);
      skillRepository.save(skill);
      return getMapper().toDTO(skill);
    }
    return saved;
  }

  private List<Ferramenta> resolveFerramentas(List<FerramentaDTO> ferramentaDtos) {
    List<Ferramenta> result = new ArrayList<>();
    for (FerramentaDTO f : ferramentaDtos) {
      if (f.getId() != null) {
        getConfig().getFerramentaRepository().findById(f.getId()).ifPresent(result::add);
      } else if (f.getIdentificador() != null) {
        getConfig().getFerramentaRepository().findByIdentificador(f.getIdentificador()).ifPresent(result::add);
      }
    }
    return result;
  }

  @Override
  public SkillServiceConfig getConfig() {
    return (SkillServiceConfig) super.getConfig();
  }
}
