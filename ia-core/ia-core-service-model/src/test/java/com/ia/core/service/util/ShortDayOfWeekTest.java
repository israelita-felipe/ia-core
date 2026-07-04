package com.ia.core.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ShortDayOfWeek.
 */
@DisplayName("ShortDayOfWeek Tests")
class ShortDayOfWeekTest {

  @Test
  @DisplayName("deve ter valores definidos")
  void testEnumValues() {
    assertThat(ShortDayOfWeek.values()).containsExactly(
      ShortDayOfWeek.DOM,
      ShortDayOfWeek.SEG,
      ShortDayOfWeek.TER,
      ShortDayOfWeek.QUA,
      ShortDayOfWeek.QUI,
      ShortDayOfWeek.SEX,
      ShortDayOfWeek.SAB
    );
  }

  @Test
  @DisplayName("deve ter valor DOM")
  void testDomValue() {
    assertThat(ShortDayOfWeek.DOM).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor SEG")
  void testSegValue() {
    assertThat(ShortDayOfWeek.SEG).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor TER")
  void testTerValue() {
    assertThat(ShortDayOfWeek.TER).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor QUA")
  void testQuaValue() {
    assertThat(ShortDayOfWeek.QUA).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor QUI")
  void testQuiValue() {
    assertThat(ShortDayOfWeek.QUI).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor SEX")
  void testSexValue() {
    assertThat(ShortDayOfWeek.SEX).isNotNull();
  }

  @Test
  @DisplayName("deve ter valor SAB")
  void testSabValue() {
    assertThat(ShortDayOfWeek.SAB).isNotNull();
  }
}
