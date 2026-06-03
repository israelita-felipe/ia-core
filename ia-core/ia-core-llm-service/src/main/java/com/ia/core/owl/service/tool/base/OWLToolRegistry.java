package com.ia.core.owl.service.tool.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Registry para todas as tools OWL 2 DL disponíveis.
 * <p>
 * Permite localizar tools por nome de construtor.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class OWLToolRegistry {

  private final Map<String, OWLTool> toolsByConstructor;

  public OWLToolRegistry(List<OWLTool> tools) {
    this.toolsByConstructor = new HashMap<>();
    for (OWLTool tool : tools) {
      toolsByConstructor.put(tool.getConstructorName(), tool);
      log.debug("Registrada tool: {} -> {}", tool.getConstructorName(), tool.getClass().getSimpleName());
    }
    log.info("Registry inicializado com {} tools OWL 2 DL", toolsByConstructor.size());
  }

  /**
   * Obtém uma tool pelo nome do construtor.
   *
   * @param constructorName nome do construtor OWL
   * @return Optional com a tool, se encontrada
   */
  public Optional<OWLTool> getTool(String constructorName) {
    return Optional.ofNullable(toolsByConstructor.get(constructorName));
  }

  /**
   * Obtém todas as tools registradas.
   *
   * @return coleção de todas as tools
   */
  public Collection<OWLTool> getAllTools() {
    return toolsByConstructor.values();
  }

  /**
   * Lista todos os nomes de construtores disponíveis.
   *
   * @return lista de nomes de construtores
   */
  public List<String> getAvailableConstructors() {
    return List.copyOf(toolsByConstructor.keySet());
  }

  /**
   * Verifica se um construtor está disponível.
   *
   * @param constructorName nome do construtor
   * @return true se disponível, false caso contrário
   */
  public boolean hasTool(String constructorName) {
    return toolsByConstructor.containsKey(constructorName);
  }
}
