package com.ia.core.quartz.view.quartz;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * @author Israel Araújo
 */
@FeignClient(name = QuartzClient.NOME, url = QuartzClient.URL)
public interface QuartzClient
  extends DefaultBaseClient<SchedulerConfigDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "quartz";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.quartz}";

  /**
   * Retorna as classes de tarefas disponíveis.
   *
   * @return Mapa com as classes de tarefas disponíveis
   */
  @GetMapping("/job-classes")
  public Map<String, String> getAvaliableJobClasses();

}
