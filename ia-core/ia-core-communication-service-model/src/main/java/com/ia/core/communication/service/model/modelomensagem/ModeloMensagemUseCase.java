package com.ia.core.communication.service.model.modelomensagem;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.usecase.CrudUseCase;

import java.util.Map;

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

  /**
   * Envia um modelo de mensagem para um grupo de contatos.
   * <p>
   * Este método aceita o ID do modelo e o ID do grupo como parâmetros,
   * delegando a chamada ao cliente Feign com os parâmetros requeridos.
   *
   * @param modeloId ID do modelo de mensagem
   * @param grupoId  ID do grupo de contatos
   */
  void enviarParaGrupo(Long modeloId, Long grupoId);

  /**
   * Envia um modelo de mensagem para um contato individual.
   * <p>
   * Este método aceita o ID do modelo e o ID do contato como parâmetros,
   * delegando a chamada ao cliente Feign com os parâmetros requeridos.
   *
   * @param modeloId  ID do modelo de mensagem
   * @param contatoId ID do contato
   */
  void enviarParaContato(Long modeloId, Long contatoId);
}


