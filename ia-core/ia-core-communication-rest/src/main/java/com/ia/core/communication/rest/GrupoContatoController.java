package com.ia.core.communication.rest;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.grupocontato.GrupoContatoService;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para operações de grupos de contatos.
 *
 * @author Israel Araújo
 */
@Slf4j
@Tag(name = "GrupoContato",
     description = "Gerenciamento de grupos de contatos")
/**
 * Classe que representa os endpoints REST para gerenciamento de grupo contato.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a GrupoContatoController
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@RestController
@RequestMapping("/api/${api.version}/grupo/contato")
public class GrupoContatoController
  extends DefaultBaseController<GrupoContato, GrupoContatoDTO> {

  public GrupoContatoController(GrupoContatoService grupoContatoService) {
    super(grupoContatoService);
  }

}
