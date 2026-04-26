package com.ia.core.communication.service.modelomensagem;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.ModeloMensagemUseCase;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemTranslator;
import com.ia.core.security.service.DefaultSecuredBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;

/**
 * Serviço para gerenciamento de modelos de mensagens.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class ModeloMensagemService
  extends DefaultSecuredBaseService<ModeloMensagem, ModeloMensagemDTO>
  implements ModeloMensagemUseCase {

  public ModeloMensagemService(ModeloMensagemServiceConfig config) {
    super(config);
  }

  @Override
  public ModeloMensagemServiceConfig getConfig() {
    return (ModeloMensagemServiceConfig) super.getConfig();
  }

  @Override
  public ModeloMensagemRepository getRepository() {
    return (ModeloMensagemRepository) super.getRepository();
  }

  @Override
  public String aplicarTemplate(Long modeloId,
                                Map<String, String> parametros) {
    log.info("Aplicando template {} com parâmetros", modeloId);
    ModeloMensagemDTO modelo = find(modeloId);
    if (modelo == null) {
      throw new RuntimeException("Modelo não encontrado: " + modeloId);
    }

    String corpo = modelo.getCorpoModelo();
    for (Map.Entry<String, String> entry : parametros.entrySet()) {
      corpo = corpo.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }
    return corpo;
  }

  @Override
  public String getFunctionalityTypeName() {
    return ModeloMensagemTranslator.MODELO_MENSAGEM;
  }

  @Override
  public void enviarParaGrupo(Long modeloId, Long grupoId) {
    log.info("Enviando modelo {} para grupo {}", modeloId, grupoId);
    getConfig().getMensagemService().enviarBatch(modeloId, grupoId);
  }

  @Override
  public void enviarParaContato(Long modeloId, Long contatoId) {
    log.info("Enviando modelo {} para contato {}", modeloId, contatoId);
    ModeloMensagemDTO modelo = find(modeloId);
    if (modelo == null) {
      throw new ServiceException("Modelo não encontrado: " + modeloId);
    }
    ContatoMensagem contato = getConfig().getContatoMensagemRepository().findById(contatoId)
        .orElseThrow(() -> new ServiceException("Contato não encontrado: " + contatoId));
    // Converter entidade para DTO
    ContatoMensagemDTO contatoDTO = getConfig().getContatoMensagemMapper().toDTO(contato);

    // Processar variáveis usando o DTO
    String conteudoProcessado = getConfig().getProcessadorVariaveis().processar(
        modelo.getCorpoModelo(),
        contatoDTO  // Agora usa HasVariavel corretamente
    );
    MensagemDTO mensagemDTO = new MensagemDTO();
    mensagemDTO.setTipoCanal(modelo.getTipoCanal());
    mensagemDTO.setTelefoneDestinatario(contato.getTelefone());
    mensagemDTO.setCorpoMensagem(conteudoProcessado);
    getConfig().getMensagemService().enviar(mensagemDTO);
  }
}
