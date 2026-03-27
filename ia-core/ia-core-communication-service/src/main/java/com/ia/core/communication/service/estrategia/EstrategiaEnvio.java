package com.ia.core.communication.service.estrategia;

import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

/**
 * Estratégia abstrata para envio de mensagens. Implementa o padrão Template
 * Method.
 *
 * @author Israel Araújo
 */
public abstract class EstrategiaEnvio {

  protected final MensagemProvider provider;

  protected EstrategiaEnvio(MensagemProvider provider) {
    this.provider = provider;
  }

  public final MensagemDTO executar(MensagemDTO dto) {
    validar(dto);
    ResultadoEnvio resultado = enviar(dto);
    return processarResultado(dto, resultado);
  }

  protected abstract void validar(MensagemDTO dto);

  protected abstract ResultadoEnvio enviar(MensagemDTO dto);

  protected abstract MensagemDTO processarResultado(MensagemDTO dto,
                                                    ResultadoEnvio resultado);

  protected void validarNaoNulo(Object obj, String mensagem) {
    if (obj == null) {
      throw new com.ia.core.model.exception.ValidationException(mensagem);
    }
  }

  protected void validarNaoVazio(String str, String mensagem) {
    if (str == null || str.isBlank()) {
      throw new com.ia.core.model.exception.ValidationException(mensagem);
    }
  }
}
