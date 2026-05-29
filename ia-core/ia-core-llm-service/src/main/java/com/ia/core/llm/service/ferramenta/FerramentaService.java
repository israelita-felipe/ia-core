package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaUseCase;
import com.ia.core.service.CrudBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento de ferramentas.
 * <p>
 * Implementa operações CRUD para ferramentas utilizadas por agentes de IA,
 * incluindo sincronização automática e listagem de ferramentas disponíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class FerramentaService
  extends CrudBaseService<Ferramenta, FerramentaDTO>
  implements FerramentaUseCase {

  public FerramentaService(FerramentaServiceConfig config) {
    super(config);
  }

  @Override
  @Tool(description = "Sincroniza as ferramentas disponíveis no sistema a partir do serviço de descoberta automática. " +
                     "O serviço de descoberta escaneia pacotes configurados em busca de beans @Tool, " +
                     "sub-agentes registrados e ferramentas MCP reportadas. Esta operação reconcilia " +
                     "o banco de dados com as ferramentas descobertas, criando novos registros, " +
                     "atualizando registros existentes e marcando como inativos os não encontrados. " +
                     "Útil após deploy de novos serviços ou alteração de configurações de scan.")
  public void syncFromDiscovery() {
    getConfig().getDiscoveryService().syncFromDiscovery();
  }

  @Override
  @Tool(description = "Lista todas as ferramentas disponíveis e ativas no sistema. " +
                     "Retorna uma lista de DTOs contendo informações sobre cada ferramenta: " +
                     "título, descrição, tipo (AGENTE_ESPECIALISTA, TOOL_SPRING, RECURSO_MCP), " +
                     "identificador, módulo de origem e status de ativação. Apenas ferramentas " +
                     "com campo ativo=true são retornadas. Útil para associação a skills " +
                     "e para visualização do catálogo de capacidades disponíveis para o orquestrador.")
  public List<FerramentaDTO> listAvailable() {
    return getMapper().toDTOList(getRepository().findByAtivoTrue());
  }

  public FerramentaServiceConfig getConfig() {
    return (FerramentaServiceConfig) super.getConfig();
  }

  public FerramentaRepository getRepository() {
    return getConfig().getRepository();
  }
}
