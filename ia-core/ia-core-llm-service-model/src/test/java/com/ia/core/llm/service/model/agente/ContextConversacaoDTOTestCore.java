package com.ia.core.llm.service.model.agente;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe ContextConversacaoDTO.
 */
@DisplayName("Testes de ContextConversacaoDTO")
class ContextConversacaoDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<ContextConversacaoDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
