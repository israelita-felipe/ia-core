package com.ia.core.flyway.view.flywayexecution;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Cliente Feign para operações de FlywayExecution.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de monitoramento de
 * execuções do Flyway. Fornece operações de consulta ao histórico de
 * migrações do banco de dados.
 *
 * @author Israel Araújo
 * @see DefaultBaseClient
 */
@FeignClient(name = FlywayExecutionClient.NOME, url = FlywayExecutionClient.URL)
public interface FlywayExecutionClient extends DefaultBaseClient<FlywayExecutionDTO> {

	/**
	 * Nome do cliente.
	 */
	public static final String NOME = "flyway-execution";

	/**
	 * URL do cliente.
	 */
	public static final String URL = "${feign.host}/api/${api.version}/${feign.url.flyway-execution}";

	/**
	 * Lista todas as execuções de migrations.
	 *
	 * @return lista de execuções
	 */
	List<FlywayExecutionDTO> listAll();

	/**
	 * Lista apenas as execuções bem-sucedidas.
	 *
	 * @return lista de execuções bem-sucedidas
	 */
	List<FlywayExecutionDTO> listSuccessful();

	/**
	 * Lista apenas as execuções falhadas.
	 *
	 * @return lista de execuções falhadas
	 */
	List<FlywayExecutionDTO> listFailed();
}