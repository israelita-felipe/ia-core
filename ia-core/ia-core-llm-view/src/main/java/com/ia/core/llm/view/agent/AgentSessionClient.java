package com.ia.core.llm.view.agent;

import com.ia.core.llm.service.model.agent.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agent.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agent.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
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

  @GetMapping("/skills")
  @Resilient(ResilienceProfile.LLM_SERVICE)
  List<SkillMetadataDTO> listAvailableSkills();
}
