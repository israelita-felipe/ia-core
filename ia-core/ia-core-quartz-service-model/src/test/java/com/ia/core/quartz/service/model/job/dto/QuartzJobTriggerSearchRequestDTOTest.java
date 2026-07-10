package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("QuartzJobTriggerSearchRequest")
class QuartzJobTriggerSearchRequestTest {

  @Test
  @DisplayName("Should expose job trigger filters")
  void shouldExposeJobTriggerFilters() {
    Map<FilterProperty, ?> filters = new QuartzJobTriggerSearchRequestDTO().getAvaliableFilters();

    assertThat(filters.keySet())
        .extracting(FilterProperty::getProperty)
        .containsExactlyInAnyOrder("triggerName", "triggerGroup", "jobName", "triggerState");
    assertThat(new QuartzJobTriggerDTO().propertyFilters())
        .containsExactlyInAnyOrder("triggerName", "triggerGroup", "jobName", "triggerState");
  }
}
