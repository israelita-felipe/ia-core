package com.ia.core.llm.view.validacao;

import com.ia.core.llm.service.model.ontologia.ResultadoValidacao;
import com.ia.core.view.manager.DefaultBaseManager;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoManager extends DefaultBaseManager<ResultadoValidacao> {

  public ValidacaoManager(ValidacaoManagerConfig config) {
    super(config);
  }
}
