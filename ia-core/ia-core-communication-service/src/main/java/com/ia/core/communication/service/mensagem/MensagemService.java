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
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.security.service.CrudSecuredBaseService;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
  extends CrudSecuredBaseService<Mensagem, MensagemDTO>
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
  @Tool(description = "Envia uma mensagem através do canal especificado (Email, SMS, WhatsApp, Telegram). " +
             "Processa a mensagem através da estratégia de envio apropriada para o tipo de canal. " +
             "Salva a mensagem no banco de dados antes do envio e atualiza o status após o envio. " +
             "Útil para envio de mensagens individuais com rastreamento de status. " +
             "Retorna a mensagem com status atualizado (PENDENTE, ENVIADA ou FALHA).")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public MensagemDTO enviar(
          @ToolParam(description = "Dados da mensagem a ser enviada (MensagemDTO, obrigatório). " +
                            "Inclui tipoCanal (EMAIL, SMS, WHATSAPP, TELEGRAM), telefoneDestinatario e corpoMensagem.", required = true) MensagemDTO dto) {
    log.info("Enviando mensagem via {} para {}", dto.getTipoCanal(),
             dto.getTelefoneDestinatario());

    EstrategiaEnvio estrategia = null;
    // Usa a factory de estratégias para validar e obter estratégia
    try {
      var factory = getConfig().getEstrategiaEnvioFactory();
      if (factory == null) {
        log.error("EstrategiaEnvioFactory não configurada");
        dto.setStatusMensagem(StatusMensagem.FALHA);
        dto.setMotivoFalha("EstrategiaEnvioFactory não configurada");
        return save(dto);
      }
      estrategia = factory.criarEstrategia(dto.getTipoCanal());
      // Validação acontece durante a obtenção da estratégia
    } catch (IllegalArgumentException e) {
      log.error("Canal não suportado: {}", dto.getTipoCanal());
      dto.setStatusMensagem(StatusMensagem.FALHA);
      dto.setMotivoFalha(e.getMessage());
      return save(dto);
    } catch (Exception e) {
      log.error("Erro ao preparar mensagem: {}", e.getMessage());
      dto.setStatusMensagem(StatusMensagem.FALHA);
      dto.setMotivoFalha(e.getMessage());
      return save(dto);
    }

    // Marca como pendente antes de salvar
    dto.setStatusMensagem(StatusMensagem.PENDENTE);
    MensagemDTO saved = save(dto);

    // Executa o envio usando a entidade persistida
    try {
      saved = estrategia.executar(saved);
      saved = save(saved);
    } catch (Exception e) {
      log.error("Erro ao enviar mensagem: {}", e.getMessage());
      saved.setStatusMensagem(StatusMensagem.FALHA);
      saved.setMotivoFalha(e.getMessage());
      saved = save(saved);
    }
    return saved;
  }

  @Override
  @Tool(description = "Envia mensagens em massa para uma lista de destinatários através do canal especificado. " +
             "Processa cada mensagem individualmente e retorna um resumo do envio com total de mensagens e detalhes de falhas. " +
             "Útil para campanhas de comunicação, notificações em lote e broadcasts. " +
             "Retorna resposta com quantidade de envios bem-sucedidos e lista de falhas se houver.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public EnvioMensagemResponseDTO enviarEmMassa(
          @ToolParam(description = "Dados do envio em massa (EnvioMensagemRequestDTO, obrigatório). " +
                          "Inclui tipoCanal, corpoMensagem e lista de telefones dos destinatários.", required = true) EnvioMensagemRequestDTO request) {
    Objects.requireNonNull(request, "Request não pode ser null");
    Objects.requireNonNull(request.getTelefones(), "Lista de telefones não pode ser null");
    log.info("Enviando {} mensagens em massa", request.getTelefones().size());
    List<String> falhas = new ArrayList<>();

    for (String telefone : request.getTelefones()) {
      try {
        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setTipoCanal(request.getTipoCanal());
        mensagemDTO.setTelefoneDestinatario(telefone);
        mensagemDTO.setCorpoMensagem(request.getCorpoMensagem());
        this.enviar(mensagemDTO);
      } catch (Exception e) {
        falhas.add("Telefone " + telefone + ": " + e.getMessage());
      }
    }

    if (falhas.isEmpty()) {
      return EnvioMensagemResponseDTO.sucesso(request.getTelefones().size());
    } else {
      return EnvioMensagemResponseDTO.comFalhas(request.getTelefones().size() - falhas.size(), falhas);
    }
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
  @Tool(description = "Envia mensagens em lote para todos os contatos de um grupo usando um modelo de mensagem. " +
             "Processa variáveis do modelo para cada contato individualmente, permitindo personalização. " +
             "Útil para campanhas personalizadas, newsletters e comunicações direcionadas. " +
             "Retorna mensagem de sucesso ou lista de falhas por contato.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public String enviarBatch(
          @ToolParam(description = "ID do modelo de mensagem a ser usado (Long, obrigatório). " +
                          "Identifica o template com variáveis a serem processadas.", required = true) Long modeloId,
          @ToolParam(description = "ID do grupo de contatos (Long, obrigatório). " +
                          "Identifica o grupo contendo os destinatários.", required = true) Long grupoId) {
    Objects.requireNonNull(modeloId, "Modelo ID não pode ser null");
    Objects.requireNonNull(grupoId, "Grupo ID não pode ser null");
    var grupoRepo = getGrupoContatoRepository();
    var modeloRepo = getModeloMensagemRepository();
    if (grupoRepo == null || modeloRepo == null) {
      return "Erros de configuração: repositórios não inicializados";
    }
    ModeloMensagem modeloMensagem = modeloRepo.findById(modeloId)
        .orElseThrow(() -> new ServiceException("Modelo de mensagem não encontrado"));
    GrupoContato grupo = grupoRepo.findById(grupoId)
        .orElseThrow(() -> new ServiceException("Grupo não encontrado"));
    List<String> falhas = new ArrayList<>();

    List<ContatoMensagem> contatos = grupo.getContatos();
    if (contatos == null || contatos.isEmpty()) {
      return "Batch enviado com sucesso (nenhum contato no grupo)";
    }

    var mapper = getContatoMensagemMapper();
    var processador = getProcessadorVariaveis();
    var corpoModelo = modeloMensagem.getCorpoModelo() != null ? modeloMensagem.getCorpoModelo() : "";

    for (ContatoMensagem contato : contatos) {
      try {
        // Converter entidade para DTO (que implementa HasVariavel)
        ContatoMensagemDTO contatoDTO = mapper != null ? mapper.toDTO(contato) : null;
        if (contatoDTO == null) {
          falhas.add("Contato sem mapper: " + (contato != null ? contato.getTelefone() : "null"));
          continue;
        }

        // Processar variáveis usando o DTO
        String conteudoProcessado;
        if (processador != null) {
          conteudoProcessado = processador.processar(corpoModelo, contatoDTO);
        } else {
          conteudoProcessado = corpoModelo;
        }

        // Criar e enviar mensagem
        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setTipoCanal(modeloMensagem.getTipoCanal());
        mensagemDTO.setTelefoneDestinatario(contato.getTelefone() != null ? contato.getTelefone() : "");
        mensagemDTO.setCorpoMensagem(conteudoProcessado);
        this.enviar(mensagemDTO);
      } catch (Exception e) {
        falhas.add("Contato " + contato.getTelefone() + ": " + e.getMessage());
      }
    }
    return falhas.isEmpty() ? "Batch enviado com sucesso" : "Batch com falhas: " + String.join(", ", falhas);
  }
}
