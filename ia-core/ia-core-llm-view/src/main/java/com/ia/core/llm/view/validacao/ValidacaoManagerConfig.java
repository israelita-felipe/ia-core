package com.ia.core.llm.view.validacao;

import com.ia.core.llm.service.model.ontologia.ResultadoValidacaoDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoManagerConfig extends DefaultBaseManagerConfig<ResultadoValidacaoDTO> {

  public ValidacaoManagerConfig(ValidacaoClient client) {
    super(client);
  }
}
