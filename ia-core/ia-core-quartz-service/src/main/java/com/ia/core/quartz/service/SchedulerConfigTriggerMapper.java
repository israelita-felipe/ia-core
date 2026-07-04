package com.ia.core.quartz.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ia.core.quartz.model.scheduler.SchedulerConfigTrigger;
import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import java.lang.reflect.Type;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * {@link Mapper} de {@link SchedulerConfigTrigger} para {@link SchedulerConfigTriggerDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchedulerConfigTriggerMapper
  extends BaseEntityMapper<SchedulerConfigTrigger, SchedulerConfigTriggerDTO> {

  Gson GSON = new Gson();
  Type JOB_DATA_TYPE = new TypeToken<Map<String, Object>>() { }.getType();

  /**
   * Mapeia {@code triggerStartTime} para {@code startTime}.
   */
  @Mapping(target = "startTime", source = "triggerStartTime")
  @Override
  SchedulerConfigTriggerDTO toDTO(SchedulerConfigTrigger trigger);

  /**
   * Mapeia {@code startTime} para {@code triggerStartTime}.
   */
  @Mapping(target = "triggerStartTime", source = "startTime")
  @Override
  SchedulerConfigTrigger toModel(SchedulerConfigTriggerDTO dto);

  default Map<String, Object> map(String jobData) {
    if (jobData == null || jobData.isBlank()) {
      return null;
    }
    try {
      return GSON.fromJson(jobData, JOB_DATA_TYPE);
    } catch (JsonSyntaxException ex) {
      return Map.of("raw", jobData);
    }
  }

  default String map(Map<String, Object> jobData) {
    return jobData == null ? null : GSON.toJson(jobData);
  }
}
