package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.model.ferramenta.FerramentaDiscoverable;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.mapper.SearchRequestMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para descoberta automática de ferramentas.
 * <p>
 * Responsável por descobrir e sincronizar ferramentas Spring AI (@Tool)
 * e agentes especialistas automaticamente com o banco de dados.
 * Recupera ferramentas tanto do contexto Spring quanto do banco de dados
 * através da interface FerramentaDiscoverable, com suporte a paginação.
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
  private final SearchRequestMapper searchRequestMapper;
  private final FerramentaMapper ferramentaMapper;

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
    log.info("Iniciando sincronização de ferramentas. enabled: {}",
        llmModuleProperties.getFerramenta().getDiscovery().isEnabled());

    if (!llmModuleProperties.getFerramenta().getDiscovery().isEnabled()) {
      log.warn("Descoberta de ferramentas está desabilitada");
      return;
    }

    List<String> scanPackages = llmModuleProperties.getFerramenta().getDiscovery().getScanPackages();
    log.info("Pacotes de scan: {}", scanPackages);

    DiscoveryResult result = discoverSpringTools(scanPackages);
    log.info("Sincronização concluída. Ferramentas: {}, Beans verificados: {}",
        result.ferramentaCount(), result.beansChecked());
  }

  private DiscoveryResult discoverSpringTools(List<String> scanPackages) {
    int ferramentaCount = 0;
    int beansChecked = 0;

    log.info("Total de beans no ApplicationContext: {}", applicationContext.getBeanDefinitionNames().length);

    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Class<?> type = applicationContext.getType(beanName);
      if (type == null || !isInScanPackages(type, scanPackages)) {
        continue;
      }

      beansChecked++;
      log.debug("Verificando bean: {} do tipo: {}", beanName, type.getName());

      for (Method method : ReflectionUtils.getAllDeclaredMethods(type)) {
        Tool tool = method.getAnnotation(Tool.class);
        if (tool != null) {
          ToolInfo info = extractToolInfo(tool, type, method);
          log.info("Ferramenta descoberta: {} em método: {} da classe: {}",
              info.id(), method.getName(), type.getSimpleName());
          upsertTool(info);
          ferramentaCount++;
        }
      }
    }

    return new DiscoveryResult(ferramentaCount, beansChecked);
  }

  private boolean isInScanPackages(Class<?> type, List<String> scanPackages) {
    String pkg = type.getPackageName();
    if (scanPackages == null || scanPackages.isEmpty()) {
      return false;
    }
    boolean result = scanPackages.stream().anyMatch(pkg::startsWith);
    log.debug("Pacote: {} está nos pacotes de scan: {}", pkg, result);
    return result;
  }

  private ToolInfo extractToolInfo(Tool tool, Class<?> type, Method method) {
    String id = tool.name().isBlank() ? type.getSimpleName() + "." + method.getName() : tool.name();
    String desc = tool.description().isBlank() ? method.getName() : tool.description();
    return new ToolInfo(id, desc, type.getSimpleName());
  }

  private void upsertTool(ToolInfo info) {
    ferramentaRepository.findByIdentificador(info.id())
        .ifPresentOrElse(
            existing -> updateExisting(existing, info),
            () -> createNew(info));
  }

  private void createNew(ToolInfo info) {
    Ferramenta nova = Ferramenta.builder()
        .titulo(info.id())
        .descricao(info.description())
        .identificador(info.id())
        .moduloOrigem(info.module())
        .tipo(TipoFerramentaEnum.TOOL_SPRING)
        .ativo(true)
        .descobertaAutomatica(true)
        .build();
    ferramentaRepository.save(nova);
  }

  private void updateExisting(Ferramenta existing, ToolInfo info) {
    existing.setTitulo(info.id());
    existing.setDescricao(info.description());
    existing.setModuloOrigem(info.module());
    existing.setTipo(TipoFerramentaEnum.TOOL_SPRING);
    existing.setDescobertaAutomatica(true);
    ferramentaRepository.save(existing);
  }

  /**
   * Lista todas as ferramentas descobertas (do contexto Spring e do banco de dados)
   * com suporte a paginação e filtros usando o padrão SearchRequest.
   *
   * @param searchRequestDTO requisição de busca com filtros, ordenação e paginação
   * @return página de ferramentas descobertas
   */
  @TransactionalWrite
  public Page<FerramentaDiscoverable> listDiscoverable(SearchRequestDTO searchRequestDTO) {
    log.debug("Listando ferramentas descobertas com SearchRequest: {}", searchRequestDTO);

    // Converte SearchRequestDTO para SearchRequest
    SearchRequest searchRequest = searchRequestMapper.toModel(searchRequestDTO);

    // Cria Pageable a partir de SearchRequest
    Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());

    // Recupera ferramentas do banco de dados com paginação
    Page<Ferramenta> dbFerramentas = ferramentaRepository.findAll(pageable);

    // Converte para FerramentaDiscoverable (FerramentaDTO implementa a interface)
    return dbFerramentas.map(ferramenta -> {
      com.ia.core.llm.service.model.ferramenta.FerramentaDTO dto =
          ferramentaMapper.toDTO(ferramenta);
      return dto;
    });
  }

  /**
   * Lista beans Spring que implementam FerramentaDiscoverable.
   * Útil para descoberta de ferramentas registradas no contexto Spring.
   *
   * @return lista de beans FerramentaDiscoverable do contexto Spring
   */
  public List<FerramentaDiscoverable> listSpringDiscoverableBeans() {
    List<FerramentaDiscoverable> discoverables = new ArrayList<>();

    for (String beanName : applicationContext.getBeanDefinitionNames()) {
      Object bean = applicationContext.getBean(beanName);
      if (bean instanceof FerramentaDiscoverable) {
        discoverables.add((FerramentaDiscoverable) bean);
        log.debug("Bean descoberto implementando FerramentaDiscoverable: {}", beanName);
      }
    }

    log.debug("Total de beans FerramentaDiscoverable encontrados: {}", discoverables.size());
    return discoverables;
  }

  private record ToolInfo(String id, String description, String module) {}
  private record DiscoveryResult(int ferramentaCount, int beansChecked) {}
}
