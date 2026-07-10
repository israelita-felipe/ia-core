package com.ia.core.quartz.service.model.periodicidade.dto;

import com.ia.core.service.dto.filter.FilterProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("PeriodicidadeSearchRequest")
class PeriodicidadeSearchRequestTest {

  @Test
  @DisplayName("Should expose periodicidade filters")
  void shouldExposePeriodicidadeFilters() {
    Map<FilterProperty, ?> filters = new PeriodicidadeSearchRequestDTO().getAvaliableFilters();

    assertThat(filters.keySet())
        .extracting(FilterProperty::getProperty)
        .containsExactlyInAnyOrder("zoneId", "intervaloBase.startTime", "intervaloBase.endTime", "regra.frequency", "regra.intervalValue");
    assertThat(new PeriodicidadeDTO().propertyFilters())
        .containsExactlyInAnyOrder("zoneId");
  }
}
