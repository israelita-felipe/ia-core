package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("QuartzJobSearchRequest")
class QuartzJobSearchRequestTest {

  @Test
  @DisplayName("Should expose job filters")
  void shouldExposeJobFilters() {
    Map<FilterProperty, ?> filters = new QuartzJobSearchRequest().getAvaliableFilters();

    assertThat(filters.keySet())
        .extracting(FilterProperty::getProperty)
        .containsExactlyInAnyOrder("jobName", "jobGroup", "jobState");
    assertThat(new QuartzJobDTO().propertyFilters())
        .containsExactlyInAnyOrder("jobName", "jobGroup", "jobState");
  }
}
