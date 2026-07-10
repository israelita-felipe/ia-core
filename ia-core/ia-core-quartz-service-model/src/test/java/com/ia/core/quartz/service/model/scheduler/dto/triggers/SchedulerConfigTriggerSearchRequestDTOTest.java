package com.ia.core.quartz.service.model.scheduler.dto.triggers;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("SchedulerConfigTriggerSearchRequest")
class SchedulerConfigTriggerSearchRequestTest {

  @Test
  @DisplayName("Should expose scheduler trigger filters")
  void shouldExposeSchedulerTriggerFilters() {
    Map<FilterProperty, ?> filters = new SchedulerConfigTriggerSearchRequestDTO().getAvaliableFilters();

    assertThat(filters.keySet())
        .extracting(FilterProperty::getProperty)
        .containsExactly("triggerName");
    assertThat(new SchedulerConfigTriggerDTO().propertyFilters())
        .containsExactly("triggerName");
  }
}
