package com.ia.core.communication.service.sms;


import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.estrategia.EstrategiaEnvio;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Estratégia de envio para SMS.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para estrategia sms.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a EstrategiaSms
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Slf4j
@Component
public class EstrategiaSms
  extends EstrategiaEnvio {

  public EstrategiaSms(SmsService provider) {
    super(provider);
  }

  @Override
  protected void validar(MensagemDTO dto) {
    validarNaoVazio(dto.getTelefoneDestinatario(),
                    "Telefone do destinatário é obrigatório");
    validarNaoVazio(dto.getCorpoMensagem(),
                    "Corpo da mensagem é obrigatório");
    if (dto.getTipoCanal() != null && dto.getTipoCanal() != TipoCanal.SMS) {
      throw new com.ia.core.model.exception.ValidationException("Canal incompatível com estratégia SMS");
    }
  }

  @Override
  protected ResultadoEnvio enviar(MensagemDTO dto) {
    log.info("Enviando SMS para {}", dto.getTelefoneDestinatario());
    return provider.enviar(dto);
  }

  @Override
  protected MensagemDTO processarResultado(MensagemDTO dto,
                                           ResultadoEnvio resultado) {
    if (resultado.isSucesso()) {
      dto.setIdExterno(resultado.getMessageId());
      dto.setStatusMensagem(StatusMensagem.ENVIADA);
      dto.setDataEnvio(LocalDateTime.now());
      log.info("SMS enviado com sucesso: {}", resultado.getMessageId());
    } else {
      dto.setStatusMensagem(StatusMensagem.FALHA);
      dto.setMotivoFalha(resultado.getErro());
      log.error("Falha ao enviar SMS: {}", resultado.getErro());
    }
    return dto;
  }
}
