package com.ia.core.communication.view.modelomensagem;

import com.ia.core.communication.service.model.modelomensagem.ModeloMensagemUseCase;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;
import org.springframework.stereotype.Service;

import java.util.Map;

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

  @Override
  public void enviarParaGrupo(Long modeloId, Long grupoId) {
    if (modeloId != null && grupoId != null) {
      getClient().enviarParaGrupo(modeloId, grupoId);
    }
  }

  @Override
  public void enviarParaContato(Long modeloId, Long contatoId) {
    if (modeloId != null && contatoId != null) {
      getClient().enviarParaContato(modeloId, contatoId);
    }
  }

}


