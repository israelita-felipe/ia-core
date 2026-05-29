package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.config.LlmModuleProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    if (llmModuleProperties.getFerramenta().getDiscovery().isRefreshOnStartup()) {
      syncFromDiscovery();
    }
  }

  @Transactional
  public void syncFromDiscovery() {
    if (!llmModuleProperties.getFerramenta().getDiscovery().isEnabled()) {
      return;
    }
    discoverSpringTools();
    log.info("Sincronização de ferramentas concluída");
  }

  private void discoverSpringTools() {
    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Class<?> type = applicationContext.getType(beanName);
      if (type == null || !isInScanPackages(type)) {
        continue;
      }
      for (Method method : type.getDeclaredMethods()) {
        Tool tool = method.getAnnotation(Tool.class);
        if (tool != null) {
          String id = tool.name().isBlank() ? type.getSimpleName() + "." + method.getName() : tool.name();
          String desc = tool.description().isBlank() ? method.getName() : tool.description();
          upsertTool(id, desc, type.getSimpleName());
        }
      }
    }
  }

  private boolean isInScanPackages(Class<?> type) {
    String pkg = type.getPackageName();
    return llmModuleProperties.getFerramenta().getDiscovery().getScanPackages().stream()
        .anyMatch(pkg::startsWith);
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
