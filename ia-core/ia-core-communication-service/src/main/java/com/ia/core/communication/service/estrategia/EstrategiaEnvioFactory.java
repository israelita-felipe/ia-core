package com.ia.core.communication.service.estrategia;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.email.EstrategiaEmail;
import com.ia.core.communication.service.sms.EstrategiaSms;
import com.ia.core.communication.service.telegram.EstrategiaTelegram;
import com.ia.core.communication.service.whatsapp.EstrategiaWhatsApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Factory para criação de estratégias de envio baseadas no tipo de canal.
 * <p>
 * Implementa o padrão Factory Method para criar instâncias de estratégias
 * de envio específicas para cada canal de comunicação suportado.
 * <p>
 * Canais suportados:
 * <ul>
 *   <li>WHATSAPP - Envio via WhatsApp</li>
 *   <li>SMS - Envio via SMS</li>
 *   <li>EMAIL - Envio via E-mail</li>
 *   <li>TELEGRAM - Envio via Telegram</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EstrategiaEnvioFactory {

  /** Estratégia de envio para WhatsApp */
  private final EstrategiaWhatsApp estrategiaWhatsApp;
  /** Estratégia de envio para SMS */
  private final EstrategiaSms estrategiaSms;
  /** Estratégia de envio para E-mail */
  private final EstrategiaEmail estrategiaEmail;
  /** Estratégia de envio para Telegram */
  private final EstrategiaTelegram estrategiaTelegram;

  /**
   * Cria uma estratégia de envio baseada no tipo de canal.
   * <p>
   * Retorna a implementação específica de estratégia para o canal solicitado.
   *
   * @param tipoCanal tipo do canal de comunicação (não pode ser nulo)
   * @return estratégia de envio correspondente
   * @throws IllegalArgumentException se o canal não for suportado ou for nulo
   */
  public EstrategiaEnvio criarEstrategia(TipoCanal tipoCanal) {
    log.debug("Criando estratégia para o canal: {}", tipoCanal);

    if (tipoCanal == null) {
      throw new IllegalArgumentException("Tipo de canal não pode ser nulo");
    }

    return switch (tipoCanal) {
    case WHATSAPP -> estrategiaWhatsApp;
    case SMS -> estrategiaSms;
    case EMAIL -> estrategiaEmail;
    case TELEGRAM -> estrategiaTelegram;
    case WEBHOOK -> throw new IllegalArgumentException("Canal WEBHOOK não suportado para envio");
    };
  }

  /**
   * Verifica se um canal é suportado para envio.
   * <p>
   * O canal WEBHOOK não é suportado para envio direto de mensagens.
   *
   * @param tipoCanal tipo do canal de comunicação (pode ser nulo)
   * @return true se o canal for suportado, false caso contrário
   */
  public boolean isCanalSuportado(TipoCanal tipoCanal) {
    return tipoCanal != null && tipoCanal != TipoCanal.WEBHOOK;
  }
}
