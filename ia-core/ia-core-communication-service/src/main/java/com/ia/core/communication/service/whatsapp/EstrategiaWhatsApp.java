package com.ia.core.communication.service.whatsapp;


import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.estrategia.EstrategiaEnvio;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Estratégia de envio para WhatsApp.
 *
 * @author Israel Araújo
 */
@Slf4j
@Component
public class EstrategiaWhatsApp
  extends EstrategiaEnvio {

  public EstrategiaWhatsApp(WhatsAppService provider) {
    super(provider);
  }

  @Override
  protected void validar(MensagemDTO dto) {
    validarNaoVazio(dto.getTelefoneDestinatario(),
                    "Telefone do destinatário é obrigatório");
    validarNaoVazio(dto.getCorpoMensagem(),
                    "Corpo da mensagem é obrigatório");
    if (dto.getTipoCanal() != null
        && dto.getTipoCanal() != TipoCanal.WHATSAPP) {
      throw new com.ia.core.model.exception.ValidationException("Canal incompatível com estratégia WhatsApp");
    }
  }

  @Override
  protected ResultadoEnvio enviar(MensagemDTO dto) {
    log.info("Enviando mensagem WhatsApp para {}",
             dto.getTelefoneDestinatario());
    return provider.enviar(dto);
  }

  @Override
  protected MensagemDTO processarResultado(MensagemDTO dto,
                                           ResultadoEnvio resultado) {
    if (resultado.isSucesso()) {
      dto.setIdExterno(resultado.getMessageId());
      dto.setStatusMensagem(StatusMensagem.ENVIADA);
      dto.setDataEnvio(LocalDateTime.now());
      log.info("Mensagem WhatsApp enviada com sucesso: {}",
               resultado.getMessageId());
    } else {
      dto.setStatusMensagem(StatusMensagem.FALHA);
      dto.setMotivoFalha(resultado.getErro());
      log.error("Falha ao enviar mensagem WhatsApp: {}",
                resultado.getErro());
    }
    return dto;
  }
}
