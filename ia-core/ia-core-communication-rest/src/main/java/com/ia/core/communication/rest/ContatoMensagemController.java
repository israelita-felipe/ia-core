package com.ia.core.communication.rest;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemService;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para operações de contatos de mensagens.
 *
 * @author Israel Araújo
 */
@Slf4j
@Tag(name = "ContatoMensagem",
     description = "Gerenciamento de contatos de mensagens")
@RestController
@RequestMapping("/api/${api.version}/contato/mensagem")
public class ContatoMensagemController
  extends DefaultBaseController<ContatoMensagem, ContatoMensagemDTO> {

  public ContatoMensagemController(ContatoMensagemService contatoMensagemService) {
    super(contatoMensagemService);
  }

}
