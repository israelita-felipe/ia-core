package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("QuartzJobInstanceSearchRequestDTO")
class QuartzJobInstanceSearchRequestDTOTest {

   @Test
   @DisplayName("Should expose job instance filters")
   void shouldExposeJobInstanceFilters() {
     Map<FilterProperty, ?> filters = new QuartzJobInstanceSearchRequestDTO().getAvaliableFilters();

     assertThat(filters.keySet())
         .extracting(FilterProperty::getProperty)
         .containsExactlyInAnyOrder("instanceId", "jobName", "jobGroup", "triggerName", "triggerGroup");
     assertThat(QuartzJobInstanceDTO.CAMPOS.INSTANCE_ID).isEqualTo("instanceId");
     assertThat(QuartzJobInstanceDTO.CAMPOS.JOB_NAME).isEqualTo("jobName");
     assertThat(QuartzJobInstanceDTO.CAMPOS.JOB_GROUP).isEqualTo("jobGroup");
   }
}
