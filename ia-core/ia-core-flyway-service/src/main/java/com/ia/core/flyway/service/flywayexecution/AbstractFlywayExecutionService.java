package com.ia.core.flyway.service.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.FlywayExecutionUseCase;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionTranslator;
import com.ia.core.model.filter.FieldType;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.CountSecuredBaseService;
import com.ia.core.security.service.FindSecuredBaseService;
import com.ia.core.security.service.ListSecuredBaseService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Serviço para gerenciar execuções de migrations do Flyway.
 * <p>
 * Este serviço fornece métodos para consultar o histórico de execuções de
 * migrações do banco de dados. Os dados são somente leitura pois são
 * gerenciados automaticamente pelo Flyway.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see FlywayExecutionUseCase
 */
@Slf4j
public abstract class AbstractFlywayExecutionService<T extends FlywayExecution, D extends FlywayExecutionDTO<T>>
  implements FindSecuredBaseService<T, D>,
  ListSecuredBaseService<T, D>,
  CountSecuredBaseService<T, D>,
  FlywayExecutionUseCase<T, D> {

  /**
   * Configuração
   */
  @Getter
  private final FlywayExecutionServiceConfig<T, D> config;

  /**
   * Construtor com injeção de dependências.
   *
   * @param config a configuração do serviço
   */
  public AbstractFlywayExecutionService(FlywayExecutionServiceConfig<T, D> config) {
    this.config = config;
  }

  /**
   * Retorna o tipo de funcionalidade para controls de segurança.
   *
   * @return o nome da funcionalidade
   */
  @Override
  public String getFunctionalityTypeName() {
    return FlywayExecutionTranslator.FLYWAY_EXECUTION;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Mapper<T, D> getMapper() {
    return getConfig().getMapper();
  }

  @SuppressWarnings("unchecked")
  @Override
  public BaseEntityRepository<T> getRepository() {
    return getConfig().getRepository();
  }

  @Override
  public SearchRequestMapper getSearchRequestMapper() {
    return getConfig().getSearchRequestMapper();
  }

  @Override
  public Translator getTranslator() {
    return getConfig().getTranslator();
  }

  @Override
  public void createContext() {

  }

  @Override
  @Tool(description = "Conta o total de execuções de migrações do Flyway baseado em critérios de busca. " +
             "Retorna o número total de execuções que correspondem aos filtros aplicados. " +
             "Útil para estatísticas de migrations, métricas de volume de alterações e relatórios de auditoria.")
  @Resilient(ResilienceProfile.DATABASE)
  public int count(
          @ToolParam(description = "Critérios de busca para contar as execuções (SearchRequestDTO, obrigatório). " +
                          "Inclui filtros opcionais por campos da execução para refinar a contagem.", required = true) SearchRequestDTO requestDTO) {
    return CountSecuredBaseService.super.count(requestDTO);
  }

  @Override
  @Tool(description = "Busca uma execução de migração do Flyway pelo ID. " +
             "Retorna informações detalhadas da execução incluindo versão, descrição, tipo de migração, " +
             "tempo de execução, data de execução, status de sucesso e mensagem de erro se houver. " +
             "Útil para inspecionar detalhes específicos de uma migração e diagnosticar problemas.")
  @Resilient(ResilienceProfile.DATABASE)
  public D find(
          @ToolParam(description = "ID único da execução de migração a ser buscada (Long, obrigatório). " +
                          "Identifica a execução específica no histórico do Flyway.", required = true) Long id) {
    return FindSecuredBaseService.super.find(id);
  }

  @Override
  @Tool(description = "Lista todas as execuções de migrações do Flyway com paginação e filtros. " +
             "Retorna uma página de execuções incluindo informações sobre versão, descrição, tipo de migração, " +
             "tempo de execução, data de execução e status de sucesso. " +
             "Útil para auditoria completa de migrations, análise de histórico de alterações no banco de dados " +
             "e monitoramento de saúde do sistema de migração.")
  @Resilient(ResilienceProfile.DATABASE)
  public Page<D> findAll(
          @ToolParam(description = "Critérios de busca e paginação (SearchRequestDTO, obrigatório). " +
                          "Inclui paginação (page, size, sort) e filtros opcionais por campos da execução.", required = true) SearchRequestDTO requestDTO) {
    return ListSecuredBaseService.super.findAll(requestDTO);
  }

  @Override
  public Map<String, String> getContextValue(Object object) {
    Map<String, String> context = new HashMap<>();
    context.putAll(FindSecuredBaseService.super.getContextValue(object));
    context.putAll(CountSecuredBaseService.super.getContextValue(object));
    context.putAll(ListSecuredBaseService.super.getContextValue(object));
    return context;
  }

  @Override
  public Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    Set<Functionality> func = new HashSet<>();
    func.addAll(FindSecuredBaseService.super.registryFunctionalities(functionalityManager));
    func.addAll(CountSecuredBaseService.super.registryFunctionalities(functionalityManager));
    func.addAll(ListSecuredBaseService.super.registryFunctionalities(functionalityManager));
    return func;
  }

  @Override
  public LogOperationService getLogOperationService() {
    return getConfig().getLogOperationService();
  }

  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  @Override
  @Tool(description = "Lista todas as execuções de migrações do Flyway que foram bem-sucedidas. " +
             "Retorna uma página de execuções com sucesso, incluindo informações sobre versão, " +
             "descrição, tipo de migração, tempo de execução e data de execução. " +
             "Útil para auditoria de migrations, verificação de histórico de alterações no banco de dados " +
             "e diagnóstico de problemas de migração. Filtra automaticamente por success = true.")
  @Resilient(ResilienceProfile.DATABASE)
  public Page<D> listSuccessful(
          @ToolParam(description = "Critérios de busca para filtrar as execuções (SearchRequestDTO, obrigatório). " +
                          "Inclui paginação (page, size, sort) e filtros adicionais. " +
                          "O filtro success=true é aplicado automaticamente.", required = true) SearchRequestDTO request) {
    request.getContext()
        .add(FilterRequestDTO.builder().fieldType(FieldType.BOOLEAN)
            .key("success").operator(OperatorDTO.EQUAL).value(Boolean.TRUE)
            .build());
    return findAll(request);
  }

  @Override
  @Tool(description = "Lista todas as execuções de migrações do Flyway que falharam. " +
             "Retorna uma página de execuções com erro, incluindo informações sobre versão, " +
             "descrição, tipo de migração, tempo de execução, data de execução e mensagem de erro. " +
             "Útil para identificar migrations problemáticas, diagnosticar falhas de migração " +
             "e priorizar correções. Filtra automaticamente por success = false.")
  @Resilient(ResilienceProfile.DATABASE)
  public Page<D> listFailed(
          @ToolParam(description = "Critérios de busca para filtrar as execuções (SearchRequestDTO, obrigatório). " +
                          "Inclui paginação (page, size, sort) e filtros adicionais. " +
                          "O filtro success=false é aplicado automaticamente.", required = true) SearchRequestDTO request) {
    request.getContext()
        .add(FilterRequestDTO.builder().fieldType(FieldType.BOOLEAN)
            .key("success").operator(OperatorDTO.EQUAL).value(Boolean.FALSE)
            .build());
    return findAll(request);
  }

}
