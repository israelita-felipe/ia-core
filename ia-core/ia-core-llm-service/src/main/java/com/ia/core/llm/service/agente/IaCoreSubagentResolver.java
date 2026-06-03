package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.agent.common.task.subagent.SubagentDefinition;
import org.springaicommunity.agent.common.task.subagent.SubagentReference;
import org.springaicommunity.agent.common.task.subagent.SubagentResolver;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Resolvedor de sub-agentes para integração spring-ai-agent-utils com padrão ia-core.
 * <p>
 * Implementa a interface SubagentResolver do spring-ai-agent-utils para resolver
 * sub-agentes a partir do banco de dados (entidade Agente) em vez de arquivos YAML.
 * Isso permite gerenciar agentes dinamicamente através da UI e API.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class IaCoreSubagentResolver
  implements SubagentResolver {

  private final AgenteService agenteService;

  public IaCoreSubagentResolver(AgenteService agenteService) {
    this.agenteService = agenteService;
  }

  @Override
  public boolean canResolve(SubagentReference subagentRef) {
    log.debug("Verificando se pode resolver sub-agente: {}", subagentRef.uri());
    // Verifica se o identificador corresponde a um agente no banco de dados
    return agenteService.findByIdentificador(subagentRef.uri()).isPresent();
  }

  @Override
  public SubagentDefinition resolve(SubagentReference subagentRef) {
    log.debug("Resolvendo sub-agente: {}", subagentRef.uri());

    Optional<AgenteDTO> agenteOpt = agenteService.findByIdentificador(subagentRef.uri());
    if (agenteOpt.isEmpty()) {
      log.warn("Agente não encontrado: {}", subagentRef.uri());
      throw new IllegalArgumentException("Agente não encontrado: " + subagentRef.uri());
    }

    AgenteDTO agente = agenteOpt.get();
    log.debug("Agente encontrado: {} ({})", agente.getTitulo(), agente.getIdentificador());

    SubagentDefinition definition = toSubagentDefinition(agente, subagentRef);
    log.debug("SubagentDefinition criada para: {}", subagentRef.uri());

    return definition;
  }

  /**
   * Converte um AgenteDTO para SubagentDefinition do spring-ai-agent-utils.
   *
   * @param agente agente DTO
   * @param reference referência do sub-agente
   * @return SubagentDefinition
   */
  private SubagentDefinition toSubagentDefinition(AgenteDTO agente, SubagentReference reference) {
    log.debug("Convertendo AgenteDTO para SubagentDefinition: identificador={}", agente.getIdentificador());

    return new IaCoreSubagentDefinition(
        agente.getIdentificador(),
        agente.getDescricao(),
        reference.kind(),
        reference,
        agente.getInstrucoes(),
        agente.getModelo(),
        agente.getFerramentas() == null
            ? Collections.emptyList()
            : agente.getFerramentas().stream()
                .map(f -> f.getIdentificador())
                .toList()
    );
  }

  /**
   * Implementação concreta de SubagentDefinition para agentes ia-core.
   */
  private record IaCoreSubagentDefinition(
      String name,
      String description,
      String kind,
      SubagentReference reference,
      String instructions,
      String model,
      List<String> tools
  ) implements SubagentDefinition {

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getDescription() {
      return description;
    }

    @Override
    public String getKind() {
      return kind;
    }

    @Override
    public SubagentReference getReference() {
      return reference;
    }
  }
}
