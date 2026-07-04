package com.ia.core.llm.view.validacao;

import com.ia.core.llm.service.model.ontologia.ResultadoValidacao;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoManagerConfig extends DefaultBaseManagerConfig<ResultadoValidacao> {

  public ValidacaoManagerConfig(ValidacaoClient client) {
    super(client);
  }
}
