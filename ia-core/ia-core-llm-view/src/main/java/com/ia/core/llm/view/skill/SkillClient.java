package com.ia.core.llm.view.skill;

import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = SkillClient.NOME, url = SkillClient.URL)
public interface SkillClient
  extends DefaultBaseClient<SkillDTO> {

  String NOME = "skill";
  String URL = "${feign.host}/api/${api.version}/${feign.url.skill}";
}
