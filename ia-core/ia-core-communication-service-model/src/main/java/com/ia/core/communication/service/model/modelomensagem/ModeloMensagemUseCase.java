package com.ia.core.communication.service.model.modelomensagem;

import java.util.Map;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para ModeloMensagem.
 * <p>
 * Define as operações específicas do domínio de modelos de mensagens conforme
 * definido no caso de uso Manter-ModeloMensagem.
 *
 * @author Israel Araújo
 */
public interface ModeloMensagemUseCase
  extends CrudUseCase<ModeloMensagemDTO> {
  String aplicarTemplate(Long modeloId, Map<String, String> parametros);
}
