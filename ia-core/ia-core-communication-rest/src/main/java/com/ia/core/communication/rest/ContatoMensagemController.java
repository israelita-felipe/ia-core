package com.ia.core.communication.rest;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemService;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para operações de contatos de mensagens.
 * <p>
 * Fornece endpoints REST para gerenciamento de contatos individuais
 * associados a grupos de contatos para envio de mensagens.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Gerenciamento completo de contatos de mensagem via CRUD</li>
 *   <li>Associação de contatos a grupos de contatos</li>
 *   <li>Armazenamento de informações de contato (telefone, nome)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Tag(name = "ContatoMensagem",
     description = "Gerenciamento de contatos de mensagens")
@RestController
@RequestMapping("/api/${api.version}/contato/mensagem")
public class ContatoMensagemController
  extends DefaultBaseController<ContatoMensagem, ContatoMensagemDTO> {

  /**
   * Construtor com dependência do serviço de contatos de mensagem.
   *
   * @param contatoMensagemService serviço de contatos de mensagem (não pode ser nulo)
   */
  public ContatoMensagemController(ContatoMensagemService contatoMensagemService) {
    super(contatoMensagemService);
  }

}
