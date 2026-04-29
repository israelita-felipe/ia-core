package com.ia.core.communication.rest;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.mensagem.MensagemService;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para operações de mensagens.
 * <p>
 * Fornece endpoints REST para gerenciamento de mensagens de comunicação,
 * incluindo envio individual e em massa.
 *
 * @author Israel Araújo
 */
@Slf4j
@Tag(name = "Mensagem",
     description = "Gerenciamento de mensagens de comunicação")
/**
 * Classe que representa os endpoints REST para gerenciamento de mensagem.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a MensagemController
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@RestController
@RequestMapping("/api/${api.version}/mensagem")
public class MensagemController
  extends DefaultBaseController<Mensagem, MensagemDTO> {

  /**
   * Construtor com dependência do serviço de mensagens.
   *
   * @param mensagemService serviço de mensagens
   */
  public MensagemController(MensagemService mensagemService) {
    super(mensagemService);
  }

  @Override
  public MensagemService getService() {
    return (MensagemService) super.getService();
  }

  /**
   * Envia uma mensagem individual.
   *
   * @param dto     dados da mensagem
   * @param request requisição HTTP
   * @return mensagem enviada com status
   */
  @PostMapping("/enviar")
  public ResponseEntity<MensagemDTO> enviar(@RequestBody MensagemDTO dto,
                                            HttpServletRequest request) {
    log.info("Recebida requisição para enviar mensagem via {}",
             dto.getTipoCanal());
    MensagemDTO resultado = getService().enviar(dto);
    return ResponseEntity.ok(resultado);
  }

  /**
   * Envia mensagens em massa.
   *
   * @param dto     dados da requisição de envio em massa
   * @param request requisição HTTP
   * @return resposta vazia indicando sucesso
   */
    @PostMapping("/enviar-massa")
    public ResponseEntity<?> enviarEmMassa(@RequestBody EnvioMensagemRequestDTO dto,
                                           HttpServletRequest request) {
      log.info("Recebida requisição para enviar mensagens em massa");
      return ResponseEntity.ok(getService().enviarEmMassa(dto));
    }

    /**
     * Envia mensagens em lote para todos os contatos de um GrupoContato usando um modelo de mensagem.
     *
     * @param modeloId ID do modelo de mensagem a ser usado
     * @param grupoId  ID do grupo de contatos
     * @return resposta indicando sucesso ou falhas parciais
     */
    @PostMapping("/batch-send")
    public ResponseEntity<?> enviarBatch(@RequestParam Long modeloId, @RequestParam Long grupoId) {
        log.info("Recebida requisição para envio em lote para modelo {} e grupo {}", modeloId, grupoId);
        try {
            String resultado = getService().enviarBatch(modeloId, grupoId);
            if (resultado.contains("falhas")) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Erro ao processar envio em lote: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar envio em lote: " + e.getMessage());
        }
    }
}
