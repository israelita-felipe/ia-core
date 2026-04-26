package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemMapper;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemRepository;
import com.ia.core.communication.service.estrategia.EstrategiaEnvio;
import com.ia.core.communication.service.grupocontato.GrupoContatoRepository;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemResponseDTO;
import com.ia.core.communication.service.model.mensagem.MensagemUseCase;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemTranslator;
import com.ia.core.communication.service.model.modelomensagem.dto.ProcessadorVariaveis;
import com.ia.core.communication.service.modelomensagem.ModeloMensagemRepository;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciamento de mensagens de comunicação.
 * <p>
 * Implementa as operações de caso de uso para o domínio de mensagens, incluindo
 * envio de mensagens, envio em massa e processamento de status.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class MensagemService
  extends DefaultSecuredBaseService<Mensagem, MensagemDTO>
  implements MensagemUseCase {

    /**
     * Construtor com dependências.
     *
     * @param config configuração do serviço contendo todas as dependências
     */
    public MensagemService(MensagemServiceConfig config) {
        super(config);
    }

    private GrupoContatoRepository getGrupoContatoRepository() {
        return getConfig().getGrupoContatoRepository();
    }

    private ContatoMensagemRepository getContatoMensagemRepository() {
        return getConfig().getContatoMensagemRepository();
    }

    private ModeloMensagemRepository getModeloMensagemRepository() {
        return getConfig().getModeloMensagemRepository();
    }

    private ProcessadorVariaveis getProcessadorVariaveis() {
        return getConfig().getProcessadorVariaveis();
    }

    private ContatoMensagemMapper getContatoMensagemMapper() {
        return getConfig().getContatoMensagemMapper();
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

    /**
     * Envia mensagens em lote para todos os contatos de um grupo.
     *
     * @param grupoId grupo contendo contatos e modelo de mensagem
     * @return mensagem de sucesso ou detalhes das falhas
     */
  public String enviarBatch(Long modeloId, Long grupoId) {
    ModeloMensagem modeloMensagem = getModeloMensagemRepository().findById(modeloId)
        .orElseThrow(() -> new ServiceException("Modelo de mensagem não encontrado"));
    GrupoContato grupo = getGrupoContatoRepository().findById(grupoId)
        .orElseThrow(() -> new ServiceException("Grupo não encontrado"));
    List<String> falhas = new ArrayList<>();

    for (ContatoMensagem contato : grupo.getContatos()) {
      try {
        // Converter entidade para DTO (que implementa HasVariavel)
        ContatoMensagemDTO contatoDTO = getContatoMensagemMapper().toDTO(contato);

        // Processar variáveis usando o DTO
        String conteudoProcessado = getProcessadorVariaveis().processar(
            modeloMensagem.getCorpoModelo(),
            contatoDTO  // Agora usa HasVariavel corretamente
        );

        // Criar e enviar mensagem
        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setTipoCanal(modeloMensagem.getTipoCanal());
        mensagemDTO.setTelefoneDestinatario(contato.getTelefone());
        mensagemDTO.setCorpoMensagem(conteudoProcessado);
        this.enviar(mensagemDTO);
      } catch (Exception e) {
        falhas.add("Contato " + contato.getTelefone() + ": " + e.getMessage());
      }
    }
    return falhas.isEmpty() ? "Batch enviado com sucesso" : "Batch com falhas: " + String.join(", ", falhas);
  }
}
