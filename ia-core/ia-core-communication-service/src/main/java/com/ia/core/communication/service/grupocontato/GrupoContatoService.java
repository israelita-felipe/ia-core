package com.ia.core.communication.service.grupocontato;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.model.grupocontato.GrupoContatoUseCase;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoTranslator;
import com.ia.core.security.service.DefaultSecuredBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciamento de grupos de contatos.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class GrupoContatoService
  extends DefaultSecuredBaseService<GrupoContato, GrupoContatoDTO>
  implements GrupoContatoUseCase {

  public GrupoContatoService(GrupoContatoServiceConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return GrupoContatoTranslator.GRUPO_CONTATO;
  }
}
