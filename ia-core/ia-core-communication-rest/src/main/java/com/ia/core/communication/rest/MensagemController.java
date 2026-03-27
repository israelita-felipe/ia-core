package com.ia.core.communication.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.communication.service.mensagem.MensagemService;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.rest.control.DefaultBaseController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

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
}
