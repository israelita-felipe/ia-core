package com.ia.core.communication.service.telegram;


import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.estrategia.EstrategiaEnvio;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Estratégia de envio para Telegram.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para estrategia telegram.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a EstrategiaTelegram
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Slf4j
@Component
public class EstrategiaTelegram
  extends EstrategiaEnvio {

  public EstrategiaTelegram(TelegramService provider) {
    super(provider);
  }

  @Override
  protected void validar(MensagemDTO dto) {
    validarNaoVazio(dto.getTelefoneDestinatario(),
                    "Chat ID do Telegram é obrigatório");
    validarNaoVazio(dto.getCorpoMensagem(),
                    "Corpo da mensagem é obrigatório");
    if (dto.getTipoCanal() != null
        && dto.getTipoCanal() != TipoCanal.TELEGRAM) {
      throw new com.ia.core.model.exception.ValidationException("Canal incompatível com estratégia Telegram");
    }
  }

  @Override
  protected ResultadoEnvio enviar(MensagemDTO dto) {
    log.info("Enviando mensagem Telegram para {}",
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
      log.info("Mensagem Telegram enviada com sucesso: {}",
               resultado.getMessageId());
    } else {
      dto.setStatusMensagem(StatusMensagem.FALHA);
      dto.setMotivoFalha(resultado.getErro());
      log.error("Falha ao enviar mensagem Telegram: {}",
                resultado.getErro());
    }
    return dto;
  }
}
