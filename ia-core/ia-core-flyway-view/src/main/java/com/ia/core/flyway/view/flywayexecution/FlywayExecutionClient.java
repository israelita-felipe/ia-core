package com.ia.core.flyway.view.flywayexecution;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Cliente Feign para operações de FlywayExecution.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de monitoramento de
 * execuções do Flyway. Fornece operações de consulta ao histórico de migrações
 * do banco de dados.
 *
 * @author Israel Araújo
 * @see DefaultBaseClient
 */
@FeignClient(name = FlywayExecutionClient.NOME,
             url = FlywayExecutionClient.URL)
public interface FlywayExecutionClient
  extends DefaultBaseClient<FlywayExecutionDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "flyway-execution";

  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.flyway-execution}";

  /**
   * Busca os elementos com paginação conforme critérios de busca.
   *
   * @param request {@link SearchRequest} - validado automaticamente via @Valid
   * @return {@link Page} com status OK (200) e dados paginados
   */
  @PostMapping("/all-successful")
  public Page<FlywayExecutionDTO> findAllSuccessful(SearchRequestDTO request);

  /**
   * Busca os elementos com paginação conforme critérios de busca.
   *
   * @param request {@link SearchRequest} - validado automaticamente via @Valid
   * @return {@link Page} com status OK (200) e dados paginados
   */
  @PostMapping("/all-failed")
  public Page<FlywayExecutionDTO> findAllFailed(SearchRequestDTO request);
}
