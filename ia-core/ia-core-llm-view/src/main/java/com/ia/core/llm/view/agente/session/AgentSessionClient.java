package com.ia.core.llm.view.agente.session;

import com.ia.core.llm.service.model.session.AgentConfirmationDTO;
import com.ia.core.llm.service.model.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AgentSessionClient {

  String NOME = "agent-session";
  String URL = "${feign.host}/api/${api.version}/${feign.url.agent-session}";

  @PostMapping("/run")
  @Resilient(ResilienceProfile.LLM_SERVICE)
  AgentSessionResponseDTO run(@RequestBody @Valid AgentSessionRequestDTO request);

  @PostMapping("/confirm")
  @Resilient(ResilienceProfile.LLM_SERVICE)
  AgentSessionResponseDTO confirm(@RequestBody @Valid AgentConfirmationDTO confirmation);

  @GetMapping("/ferramentas")
  @Resilient(ResilienceProfile.LLM_SERVICE)
  List<FerramentaMetadataDTO> listAvailableFerramentas();
}
