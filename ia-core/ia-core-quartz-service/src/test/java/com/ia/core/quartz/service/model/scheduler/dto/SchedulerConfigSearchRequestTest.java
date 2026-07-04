package com.ia.core.quartz.service.model.scheduler.dto;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("SchedulerConfigSearchRequest")
class SchedulerConfigSearchRequestTest {

  @Test
  @DisplayName("Should expose scheduler config filters")
  void shouldExposeSchedulerConfigFilters() {
    Map<FilterProperty, ?> filters = new SchedulerConfigSearchRequest().getAvaliableFilters();

    assertThat(filters.keySet())
        .extracting(FilterProperty::getProperty)
        .containsExactly("jobClassName");
    assertThat(new SchedulerConfigDTO().propertyFilters())
        .containsExactly("jobClassName");
  }
}
