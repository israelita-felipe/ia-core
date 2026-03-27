package com.ia.core.communication.view.modelomensagem;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ia.core.communication.service.model.modelomensagem.ModeloMensagemUseCase;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * Manager para operações de ModeloMensagem.
 * <p>
 * Implementa o caso de uso para gerenciamento de modelos de mensagens na
 * camada de visualização. Atua como proxy para as operações do serviço,
 * delegando chamadas ao cliente Feign.
 *
 * @author Israel Araújo
 * @see ModeloMensagemUseCase
 */
@Service
public class ModeloMensagemManager
  extends DefaultSecuredViewBaseManager<ModeloMensagemDTO>
  implements ModeloMensagemUseCase {

  public ModeloMensagemManager(ModeloMensagemManagerConfig config) {
    super(config);
  }

  @Override
  public ModeloMensagemManagerConfig getConfig() {
    return (ModeloMensagemManagerConfig) super.getConfig();
  }

  @Override
  public ModeloMensagemClient getClient() {
    return getConfig().getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return ModeloMensagemTranslator.MODELO_MENSAGEM;
  }

  @Override
  public String aplicarTemplate(Long id,
                                Map<String, String> parametros) {
    return getClient().aplicarTemplate(id, parametros);
  }

}
