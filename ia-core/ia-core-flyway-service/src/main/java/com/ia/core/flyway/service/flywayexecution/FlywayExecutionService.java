package com.ia.core.flyway.service.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.FlywayExecutionUseCase;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionTranslator;
import com.ia.core.model.filter.FieldType;
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
 * </p>
 *
 * @author Israel Araújo
 * @see FlywayExecutionUseCase
 */
@Slf4j
public class FlywayExecutionService
  implements FindSecuredBaseService<FlywayExecution, FlywayExecutionDTO>,
  ListSecuredBaseService<FlywayExecution, FlywayExecutionDTO>,
  CountSecuredBaseService<FlywayExecution, FlywayExecutionDTO>,
  FlywayExecutionUseCase {

  /**
   * Configuração
   */
  @Getter
  private final FlywayExecutionServiceConfig config;

  /**
   * Construtor com injeção de dependências.
   *
   * @param config a configuração do serviço
   */
  public FlywayExecutionService(FlywayExecutionServiceConfig config) {
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
  public Mapper<FlywayExecution, FlywayExecutionDTO> getMapper() {
    return getConfig().getMapper();
  }

  @SuppressWarnings("unchecked")
  @Override
  public BaseEntityRepository<FlywayExecution> getRepository() {
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
  public int count(SearchRequestDTO requestDTO) {
    return CountSecuredBaseService.super.count(requestDTO);
  }

  @Override
  public FlywayExecutionDTO find(Long id) {
    return FindSecuredBaseService.super.find(id);
  }

  @Override
  public Page<FlywayExecutionDTO> findAll(SearchRequestDTO requestDTO) {
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
  public Page<FlywayExecutionDTO> listSuccessful(SearchRequestDTO request) {
    request.getContext()
        .add(FilterRequestDTO.builder().fieldType(FieldType.BOOLEAN)
            .key("success").operator(OperatorDTO.EQUAL).value(Boolean.TRUE)
            .build());
    return findAll(request);
  }

  @Override
  public Page<FlywayExecutionDTO> listFailed(SearchRequestDTO request) {
    request.getContext()
        .add(FilterRequestDTO.builder().fieldType(FieldType.BOOLEAN)
            .key("success").operator(OperatorDTO.EQUAL).value(Boolean.FALSE)
            .build());
    return findAll(request);
  }

}
