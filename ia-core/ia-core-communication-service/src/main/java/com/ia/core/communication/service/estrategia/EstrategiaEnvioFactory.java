package com.ia.core.communication.service.estrategia;

import com.ia.core.communication.model.TipoCanal;
import com.ia.core.communication.service.email.EstrategiaEmail;
import com.ia.core.communication.service.sms.EstrategiaSms;
import com.ia.core.communication.service.telegram.EstrategiaTelegram;
import com.ia.core.communication.service.whatsapp.EstrategiaWhatsApp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory para criação de estratégias de envio baseadas no tipo de canal.
 * Implementa o padrão Factory Method.
 *
 * @author Israel Araújo
 */
@Slf4j
@RequiredArgsConstructor
public class EstrategiaEnvioFactory {

  private final EstrategiaWhatsApp estrategiaWhatsApp;
  private final EstrategiaSms estrategiaSms;
  private final EstrategiaEmail estrategiaEmail;
  private final EstrategiaTelegram estrategiaTelegram;

  /**
   * Cria uma estratégia de envio baseada no tipo de canal.
   *
   * @param tipoCanal tipo do canal de comunicação
   * @return estratégia de envio correspondente
   * @throws IllegalArgumentException se o canal não for suportado
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
   *
   * @param tipoCanal tipo do canal de comunicação
   * @return true se o canal for suportado
   */
  public boolean isCanalSuportado(TipoCanal tipoCanal) {
    return tipoCanal != null && tipoCanal != TipoCanal.WEBHOOK;
  }
}
