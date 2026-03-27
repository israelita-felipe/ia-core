package com.ia.core.communication.service.modelomensagem;

import java.util.Map;

import com.ia.core.communication.model.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.ModeloMensagemUseCase;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemTranslator;
import com.ia.core.security.service.DefaultSecuredBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para gerenciamento de modelos de mensagens.
 *
 * @author Israel Araújo
 */
@Slf4j
public class ModeloMensagemService
  extends DefaultSecuredBaseService<ModeloMensagem, ModeloMensagemDTO>
  implements ModeloMensagemUseCase {

  public ModeloMensagemService(ModeloMensagemServiceConfig config) {
    super(config);
  }

  @Override
  public ModeloMensagemServiceConfig getConfig() {
    return (ModeloMensagemServiceConfig) super.getConfig();
  }

  @Override
  public ModeloMensagemRepository getRepository() {
    return (ModeloMensagemRepository) super.getRepository();
  }

  @Override
  public String aplicarTemplate(Long modeloId,
                                Map<String, String> parametros) {
    log.info("Aplicando template {} com parâmetros", modeloId);
    ModeloMensagemDTO modelo = find(modeloId);
    if (modelo == null) {
      throw new RuntimeException("Modelo não encontrado: " + modeloId);
    }

    String corpo = modelo.getCorpoModelo();
    for (Map.Entry<String, String> entry : parametros.entrySet()) {
      corpo = corpo.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }
    return corpo;
  }

  @Override
  public String getFunctionalityTypeName() {
    return ModeloMensagemTranslator.MODELO_MENSAGEM;
  }
}
