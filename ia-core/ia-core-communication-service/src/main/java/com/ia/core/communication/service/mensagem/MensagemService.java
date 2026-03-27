package com.ia.core.communication.service.mensagem;

import java.util.Optional;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.communication.model.StatusMensagem;
import com.ia.core.communication.service.estrategia.EstrategiaEnvio;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemResponseDTO;
import com.ia.core.communication.service.model.mensagem.MensagemUseCase;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemTranslator;
import com.ia.core.security.service.DefaultSecuredBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para gerenciamento de mensagens de comunicação.
 * <p>
 * Implementa as operações de caso de uso para o domínio de mensagens, incluindo
 * envio de mensagens, envio em massa e processamento de status.
 *
 * @author Israel Araújo
 */
@Slf4j
public class MensagemService
  extends DefaultSecuredBaseService<Mensagem, MensagemDTO>
  implements MensagemUseCase {

  /**
   * Construtor com dependências.
   *
   * @param config                 configuração do serviço
   * @param whatsAppService        serviço do WhatsApp
   * @param estrategiaEnvioFactory fábrica de estratégias de envio
   */
  public MensagemService(MensagemServiceConfig config) {
    super(config);
  }

  @Override
  public MensagemServiceConfig getConfig() {
    return (MensagemServiceConfig) super.getConfig();
  }

  @Override
  public MensagemRepository getRepository() {
    return super.getRepository();
  }

  @Override
  public MensagemDTO enviar(MensagemDTO dto) {
    log.info("Enviando mensagem via {} para {}", dto.getTipoCanal(),
             dto.getTelefoneDestinatario());

    dto.setStatusMensagem(StatusMensagem.PENDENTE);
    MensagemDTO saved = save(dto);
    // Usa a factory de estratégias para enviar a mensagem
    try {
      EstrategiaEnvio estrategia = getConfig().getEstrategiaEnvioFactory()
          .criarEstrategia(saved.getTipoCanal());
      saved = estrategia.executar(dto);
      saved = save(saved);
    } catch (IllegalArgumentException e) {
      log.error("Canal não suportado: {}", dto.getTipoCanal());
      saved.setStatusMensagem(StatusMensagem.FALHA);
      saved.setMotivoFalha(e.getMessage());
      saved = save(saved);
    } catch (Exception e) {
      log.error("Erro ao enviar mensagem: {}", e.getMessage());
      saved.setStatusMensagem(StatusMensagem.FALHA);
      saved.setMotivoFalha(e.getMessage());
    }
    return saved;
  }

  @Override
  public EnvioMensagemResponseDTO enviarEmMassa(EnvioMensagemRequestDTO request) {
    log.info("Enviando {} mensagens em massa",
             request.getTelefones().size());
    // Implementação do envio em massa
    return EnvioMensagemResponseDTO.sucesso(request.getTelefones().size());
  }

  public Optional<Mensagem> findByIdExterno(String idExterno) {
    return getRepository().findByIdExterno(idExterno);
  }

  @Override
  public String getFunctionalityTypeName() {
    return MensagemTranslator.MENSAGEM;
  }
}
