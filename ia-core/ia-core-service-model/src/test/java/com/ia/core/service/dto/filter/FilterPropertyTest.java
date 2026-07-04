package com.ia.core.service.dto.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para FilterProperty.
 */
@DisplayName("FilterProperty Tests")
class FilterPropertyTest {

  @Nested
  @DisplayName("construtor builder")
  class Builder {

    @Test
    @DisplayName("deve criar instância com builder")
    void testBuilder() {
      FilterProperty filterProperty = FilterProperty.builder()
        .property("name")
        .label("Name")
        .build();

      assertThat(filterProperty.getProperty()).isEqualTo("name");
      assertThat(filterProperty.getLabel()).isEqualTo("Name");
    }
  }

  @Nested
  @DisplayName("construtor")
  class Constructor {

    @Test
    @DisplayName("deve criar instância com construtor")
    void testConstructor() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty.getProperty()).isEqualTo("name");
      assertThat(filterProperty.getLabel()).isEqualTo("Name");
    }

    @Test
    @DisplayName("deve criar instância com valores nulos")
    void testConstructorWithNullValues() {
      FilterProperty filterProperty = new FilterProperty(null, null);

      assertThat(filterProperty.getProperty()).isNull();
      assertThat(filterProperty.getLabel()).isNull();
    }
  }

  @Nested
  @DisplayName("getters")
  class Getters {

    @Test
    @DisplayName("deve ter getters funcionando")
    void testGetters() {
      FilterProperty filterProperty = new FilterProperty("age", "Age");

      assertThat(filterProperty.getProperty()).isEqualTo("age");
      assertThat(filterProperty.getLabel()).isEqualTo("Age");
    }
  }

  @Nested
  @DisplayName("seriabilidade")
  class Seriabilidade {

    @Test
    @DisplayName("deve implementar Serializable")
    void testImplementsSerializable() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty).isInstanceOf(Serializable.class);
    }
  }

  @Nested
  @DisplayName("equals")
  class Equals {

    @Test
    @DisplayName("deve ser igual a si mesmo")
    void testEqualsSameInstance() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty).isEqualTo(filterProperty);
    }

    @Test
    @DisplayName("deve ser igual a outro com mesmos valores")
    void testEqualsSameValues() {
      FilterProperty filterProperty1 = new FilterProperty("name", "Name");
      FilterProperty filterProperty2 = new FilterProperty("name", "Name");

      assertThat(filterProperty1).isEqualTo(filterProperty2);
    }

    @Test
    @DisplayName("deve ser diferente com property diferente")
    void testEqualsDifferentProperty() {
      FilterProperty filterProperty1 = new FilterProperty("name", "Name");
      FilterProperty filterProperty2 = new FilterProperty("age", "Age");

      assertThat(filterProperty1).isNotEqualTo(filterProperty2);
    }

    @Test
    @DisplayName("deve ser diferente com label diferente")
    void testEqualsDifferentLabel() {
      FilterProperty filterProperty1 = new FilterProperty("name", "Name");
      FilterProperty filterProperty2 = new FilterProperty("name", "Nome");

      assertThat(filterProperty1).isNotEqualTo(filterProperty2);
    }

    @Test
    @DisplayName("deve ser diferente de null")
    void testEqualsNull() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty).isNotEqualTo(null);
    }
  }

  @Nested
  @DisplayName("hashCode")
  class HashCode {

    @Test
    @DisplayName("deve ter mesmo hashCode para objetos iguais")
    void testHashCodeSameValues() {
      FilterProperty filterProperty1 = new FilterProperty("name", "Name");
      FilterProperty filterProperty2 = new FilterProperty("name", "Name");

      assertThat(filterProperty1.hashCode()).isEqualTo(filterProperty2.hashCode());
    }

    @Test
    @DisplayName("deve ter hashCode diferente para objetos diferentes")
    void testHashCodeDifferentValues() {
      FilterProperty filterProperty1 = new FilterProperty("name", "Name");
      FilterProperty filterProperty2 = new FilterProperty("age", "Age");

      assertThat(filterProperty1.hashCode()).isNotEqualTo(filterProperty2.hashCode());
    }
  }

  @Nested
  @DisplayName("toString")
  class ToString {

    @Test
    @DisplayName("deve ter toString não nulo")
    void testToStringNotNull() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty.toString()).isNotNull();
    }

    @Test
    @DisplayName("deve conter property no toString")
    void testToStringContainsProperty() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty.toString()).contains("name");
    }

    @Test
    @DisplayName("deve conter label no toString")
    void testToStringContainsLabel() {
      FilterProperty filterProperty = new FilterProperty("name", "Name");

      assertThat(filterProperty.toString()).contains("Name");
    }
  }
}
