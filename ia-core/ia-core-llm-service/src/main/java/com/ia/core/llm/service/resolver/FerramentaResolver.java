package com.ia.core.llm.service.resolver;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.ferramenta.FerramentaRepository;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilitário compartilhado para resolver entidades {@link Ferramenta} a partir de DTOs.
 * <p>
 * Centraliza a lógica de resolução que antes estava duplicada em
 * {@code AgenteService} e {@code SkillService}.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class FerramentaResolver {

  private final FerramentaRepository ferramentaRepository;

  public FerramentaResolver(FerramentaRepository ferramentaRepository) {
    this.ferramentaRepository = ferramentaRepository;
  }

  /**
   * Resolve entidades {@link Ferramenta} a partir de uma lista de DTOs,
   * buscando por {@code id} ou por {@code identificador}.
   *
   * @param ferramentaDtos lista de DTOs de ferramenta (pode ser {@code null})
   * @return lista de entidades resolvidas (nunca {@code null})
   */
  public List<Ferramenta> resolve(List<FerramentaDTO> ferramentaDtos) {
    if (ferramentaDtos == null || ferramentaDtos.isEmpty()) {
      return Collections.emptyList();
    }
    List<Ferramenta> result = new ArrayList<>();
    for (FerramentaDTO f : ferramentaDtos) {
      if (f == null) {
        continue;
      }
      if (f.getId() != null) {
        ferramentaRepository.findById(f.getId())
            .ifPresentOrElse(result::add,
                () -> log.warn("Ferramenta não encontrada por id: {}", f.getId()));
      } else if (f.getIdentificador() != null) {
        ferramentaRepository.findByIdentificador(f.getIdentificador())
            .ifPresentOrElse(result::add,
                () -> log.warn("Ferramenta não encontrada por identificador: {}", f.getIdentificador()));
      }
    }
    return result;
  }
}
