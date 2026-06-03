package com.ia.core.llm.view.agente;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

/**
 * Configuração de injeção de dependência para AgenteManager.
 * <p>
 * Fornece as dependências necessárias para o manager de agentes.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class AgenteManagerConfig
  extends DefaultBaseManagerConfig<AgenteDTO> {

  public AgenteManagerConfig(AgenteClient client) {
    super(client);
  }
}
