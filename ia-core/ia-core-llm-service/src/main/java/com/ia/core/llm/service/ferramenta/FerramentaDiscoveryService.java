package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.service.annotations.TransactionalWrite;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Serviço para descoberta automática de ferramentas.
 * <p>
 * Responsável por descobrir e sincronizar ferramentas Spring AI (@Tool)
 * e agentes especialistas automaticamente com o banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FerramentaDiscoveryService {

  private final FerramentaRepository ferramentaRepository;
  private final LlmModuleProperties llmModuleProperties;
  private final ApplicationContext applicationContext;

  @PostConstruct
  public void onStartup() {
    log.info("FerramentaDiscoveryService iniciado. refreshOnStartup: {}, enabled: {}",
        llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup(),
        llmModuleProperties.getFerramenta().getDiscovery().isEnabled());
    if (llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup()) {
      syncFromDiscovery();
    }
  }
  @TransactionalWrite
  public void syncFromDiscovery() {
    log.info("Iniciando sincronização de ferramentas. enabled: {}", llmModuleProperties.getFerramenta().getDiscovery().isEnabled());
    if (!llmModuleProperties.getFerramenta().getDiscovery().isEnabled()) {
      log.warn("Descoberta de ferramentas está desabilitada");
      return;
    }
    log.info("Pacotes de scan: {}", llmModuleProperties.getFerramenta().getDiscovery().getScanPackages());
    discoverSpringTools();
    log.info("Sincronização de ferramentas concluída");
  }

  private void discoverSpringTools() {
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
          String id = tool.name().isBlank() ? type.getSimpleName() + "." + method.getName() : tool.name();
          String desc = tool.description().isBlank() ? method.getName() : tool.description();
          log.info("Ferramenta descoberta: {} em método: {} da classe: {}", id, method.getName(), type.getSimpleName());
          upsertTool(id, desc, type.getSimpleName());
          totalFerramentas++;
        }
      }
    }
    log.info("Total de beans verificados: {}", totalBeansVerificados);
    log.info("Total de ferramentas descobertas na camada de serviço: {}", totalFerramentas);
  }

  private boolean isInScanPackages(Class<?> type) {
    String pkg = type.getPackageName();
    boolean result = llmModuleProperties.getFerramenta().getDiscovery().getScanPackages().stream()
        .anyMatch(pkg::startsWith);
    log.debug("Pacote: {} está nos pacotes de scan: {}", pkg, result);
    return result;
  }

  private void upsertTool(String identificador, String descricao, String modulo) {
    ferramentaRepository.findByIdentificador(identificador).ifPresentOrElse(
        existing -> updateExisting(existing, identificador, descricao, modulo, TipoFerramentaEnum.TOOL_SPRING),
        () -> ferramentaRepository.save(Ferramenta.builder()
            .titulo(identificador)
            .descricao(descricao)
            .identificador(identificador)
            .moduloOrigem(modulo)
            .tipo(TipoFerramentaEnum.TOOL_SPRING)
            .ativo(true)
            .descobertaAutomatica(true)
            .build()));
  }

  private void updateExisting(Ferramenta existing, String titulo, String descricao, String modulo,
                              TipoFerramentaEnum tipo) {
    existing.setTitulo(titulo);
    existing.setDescricao(descricao);
    existing.setModuloOrigem(modulo);
    existing.setTipo(tipo);
    existing.setDescobertaAutomatica(true);
    ferramentaRepository.save(existing);
  }
}
