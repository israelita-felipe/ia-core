package com.ia.core.service.dto.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para HasPropertyChangeSupport.
 */
@DisplayName("HasPropertyChangeSupport Tests")
class HasPropertyChangeSupportTest {

  @Test
  @DisplayName("deve adicionar e remover listener de propriedade específica")
  void testAddAndRemovePropertyChangeListenerWithProperty() {
    TestPropertyChangeSupport testObj = new TestPropertyChangeSupport();

    PropertyChangeListener listener = evt -> {};
    HasPropertyChangeSupport.PropertyChangeRegistry registry =
      testObj.addPropertyChangeListener("testProperty", listener);

    assertThat(registry).isNotNull();
    assertThat(testObj.getPropertyChangeSupport().getPropertyChangeListeners("testProperty"))
      .hasSize(1);

    registry.remove();

    assertThat(testObj.getPropertyChangeSupport().getPropertyChangeListeners("testProperty"))
      .isEmpty();
  }

  @Test
  @DisplayName("deve adicionar e remover listener global")
  void testAddAndRemovePropertyChangeListenerGlobal() {
    TestPropertyChangeSupport testObj = new TestPropertyChangeSupport();

    PropertyChangeListener listener = evt -> {};
    HasPropertyChangeSupport.PropertyChangeRegistry registry =
      testObj.addPropertyChangeListener(listener);

    assertThat(registry).isNotNull();
    assertThat(testObj.getPropertyChangeSupport().getPropertyChangeListeners())
      .hasSize(1);

    registry.remove();

    assertThat(testObj.getPropertyChangeSupport().getPropertyChangeListeners())
      .isEmpty();
  }

  @Test
  @DisplayName("deve disparar evento de mudança de propriedade")
  void testFirePropertyChange() {
    TestPropertyChangeSupport testObj = new TestPropertyChangeSupport();

    final boolean[] eventFired = {false};
    PropertyChangeListener listener = evt -> {
      eventFired[0] = true;
      assertThat(evt.getPropertyName()).isEqualTo("testProperty");
      assertThat(evt.getOldValue()).isEqualTo("oldValue");
      assertThat(evt.getNewValue()).isEqualTo("newValue");
    };

    testObj.addPropertyChangeListener("testProperty", listener);
    testObj.firePropertyChange("testProperty", "oldValue", "newValue");

    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("PropertyChangeRegistry deve ser interface funcional")
  void testPropertyChangeRegistryFunctionalInterface() {
    HasPropertyChangeSupport.PropertyChangeRegistry registry = () -> {};

    assertThat(registry).isNotNull();
    registry.remove();
  }

  private static class TestPropertyChangeSupport implements HasPropertyChangeSupport {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
      return propertyChangeSupport;
    }
  }
}
