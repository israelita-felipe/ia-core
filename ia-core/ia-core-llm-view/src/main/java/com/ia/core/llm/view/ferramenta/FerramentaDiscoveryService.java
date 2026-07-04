package com.ia.core.llm.view.ferramenta;

import com.ia.core.llm.view.ViewLlmModuleProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Serviço para descoberta automática de ferramentas na camada de visão.
 * <p>
 * Responsável por descobrir ferramentas Spring AI (@Tool) nos Managers
 * da camada de visão (Vaadin) e logar o total de ferramentas descobertas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FerramentaDiscoveryService {

  private final ViewLlmModuleProperties llmModuleProperties;
  private final ApplicationContext applicationContext;

  @PostConstruct
  public void onStartup() {
    log.info("FerramentaDiscoveryService (view) iniciado. refreshOnStartup: {}, enabled: {}",
        llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup(),
        llmModuleProperties.getFerramenta().getDiscovery().isEnabled());
    if (llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup()) {
      discoverSpringTools();
    }
  }

  public void discoverSpringTools() {
    log.info("Iniciando descoberta de ferramentas na camada de visão. enabled: {}", llmModuleProperties.getFerramenta().getDiscovery().isEnabled());
    if (!llmModuleProperties.getFerramenta().getDiscovery().isEnabled()) {
      log.warn("Descoberta de ferramentas na camada de visão está desabilitada");
      return;
    }
    log.info("Pacotes de scan: {}", llmModuleProperties.getFerramenta().getDiscovery().getScanPackages());
    discoverViewTools();
  }

  private void discoverViewTools() {
    int totalFerramentas = 0;
    int totalBeansVerificados = 0;
    log.info("Total de beans no ApplicationContext: {}", applicationContext.getBeanDefinitionNames().length);
    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Class<?> type = applicationContext.getType(beanName);
      if (type == null || !isInScanPackages(type)) {
        continue;
      }
      totalBeansVerificados++;
      log.debug("Verificando bean: {} do tipo: {}", beanName, type.getName());

      for (Method method : ReflectionUtils.getAllDeclaredMethods(type)) {
        Tool tool = method.getAnnotation(Tool.class);
        if (tool != null) {
          log.info("Ferramenta descoberta na camada de visão: {} em método: {} da classe: {}",
              tool.name().isBlank() ? type.getSimpleName() + "." + method.getName() : tool.name(),
              method.getName(), type.getSimpleName());
          totalFerramentas++;
        }
      }
    }
    log.info("Total de beans verificados na camada de visão: {}", totalBeansVerificados);
    log.info("Total de ferramentas descobertas na camada de visão: {}", totalFerramentas);
  }

  private boolean isInScanPackages(Class<?> type) {
    String pkg = type.getPackageName();
    boolean result = llmModuleProperties.getFerramenta().getDiscovery().getScanPackages().stream()
        .anyMatch(pkg::startsWith);
    log.debug("Pacote: {} está nos pacotes de scan: {}", pkg, result);
    return result;
  }
}
