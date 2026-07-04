package com.ia.core.quartz.service;

import com.ia.core.service.translator.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço para descoberta dinâmica de classes de job disponíveis.
 *
 * Utiliza reflections para encontrar todas as classes que estendem
 * AbstractJob no pacote "com.ia" e retorna suas traduções.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerJobDiscoveryService {

  private final Translator translator;

  /**
   * Descobre dinamicamente todas as classes de job disponíveis no pacote "com.ia".
   *
   * Utiliza reflections para encontrar todas as classes que estendem
   * AbstractJob e retorna um mapa com as classes e suas traduções.
   *
   * @return Mapa onde a chave é a classe do job e o valor é sua tradução
   */
  public Map<Class<? extends AbstractJob>, String> getAvailableJobClasses() {
    Map<Class<? extends AbstractJob>, String> jobClasses = new HashMap<>();
    Reflections reflections = new Reflections("com.ia", Scanners.SubTypes);

    reflections.getSubTypesOf(AbstractJob.class).forEach(jobClass -> {
      String translation = translator.getTranslation(jobClass.getName());
      jobClasses.put(jobClass, translation);
      log.debug("Classe de job encontrada: {} -> {}",
                jobClass.getSimpleName(), translation);
    });

    log.info("Total de classes de job disponíveis: {}", jobClasses.size());
    return jobClasses;
  }
}
