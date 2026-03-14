package com.ia.core.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.core.security.service.strategy.ContextResolveStrategy;
import com.ia.core.security.service.strategy.ContextResolveStrategyRegistry;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Tests for {@link SecurityContextService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SecurityContextService")
class SecurityContextServiceTest {

    @Mock
    private ContextResolveStrategyRegistry strategyRegistry;

    private SecurityContextService service;

    @BeforeEach
    void setUp() {
        service = new SecurityContextService(strategyRegistry);
    }

    @Nested
    @DisplayName("resolveContextValues")
    class ResolveContextValuesTests {

        @Test
        @DisplayName("Should return empty collection when context key is null")
        void shouldReturnEmptyCollectionWhenContextKeyIsNull() {
            // Given
            Collection<String> values = Collections.singletonList("value1");

            // When & Then
            assertThatThrownBy(() -> service.resolveContextValues(null, values, mock(BaseEntityRepository.class)))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Context key");
        }

        @Test
        @DisplayName("Should return empty collection when values is null")
        void shouldReturnEmptyCollectionWhenValuesIsNull() {
            // When & Then
            assertThatThrownBy(() -> service.resolveContextValues("KEY", null, mock(BaseEntityRepository.class)))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Values");
        }

        @Test
        @DisplayName("Should return empty collection when no strategy is registered")
        void shouldReturnEmptyCollectionWhenNoStrategyIsRegistered() {
            // Given
            String contextKey = "UNKNOWN_KEY";
            Collection<String> values = Collections.singletonList("value1");
            when(strategyRegistry.getStrategy(contextKey)).thenReturn(null);

            // When
            Collection<Object> result = service.resolveContextValues(contextKey, values, mock(BaseEntityRepository.class));

            // Then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should return resolved values when strategy is found")
        void shouldReturnResolvedValuesWhenStrategyIsFound() {
            // Given
            String contextKey = "TEST_KEY";
            Collection<String> values = Arrays.asList("value1", "value2");
            ContextResolveStrategy strategy = mock(ContextResolveStrategy.class);
            Collection<Object> resolvedValues = Arrays.asList("resolved1", "resolved2");
            BaseEntityRepository<?> repository = mock(BaseEntityRepository.class);

            when(strategyRegistry.getStrategy(contextKey)).thenReturn(strategy);
            when(strategy.resolveContextValues(values, repository)).thenReturn(resolvedValues);

            // When
            Collection<Object> result = service.resolveContextValues(contextKey, values, repository);

            // Then
            assertThat(result).containsExactlyInAnyOrder("resolved1", "resolved2");
            verify(strategy).resolveContextValues(values, repository);
        }
    }

    @Nested
    @DisplayName("matches")
    class MatchesTests {

        @Test
        @DisplayName("Should throw exception when context key is null")
        void shouldThrowExceptionWhenContextKeyIsNull() {
            // When & Then
            assertThatThrownBy(() -> service.matches(null, "serviceValue", "userValue"))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Context key");
        }

        @Test
        @DisplayName("Should throw exception when service context value is null")
        void shouldThrowExceptionWhenServiceContextValueIsNull() {
            // When & Then
            assertThatThrownBy(() -> service.matches("KEY", null, "userValue"))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Service context value");
        }

        @Test
        @DisplayName("Should throw exception when user context value is null")
        void shouldThrowExceptionWhenUserContextValueIsNull() {
            // When & Then
            assertThatThrownBy(() -> service.matches("KEY", "serviceValue", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("User context value");
        }

        @Test
        @DisplayName("Should return false when no strategy is registered")
        void shouldReturnFalseWhenNoStrategyIsRegistered() {
            // Given
            String contextKey = "UNKNOWN_KEY";
            when(strategyRegistry.getStrategy(contextKey)).thenReturn(null);

            // When
            boolean result = service.matches(contextKey, "serviceValue", "userValue");

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Should return true when strategy matches values")
        void shouldReturnTrueWhenStrategyMatchesValues() {
            // Given
            String contextKey = "TEST_KEY";
            ContextResolveStrategy strategy = mock(ContextResolveStrategy.class);

            when(strategyRegistry.getStrategy(contextKey)).thenReturn(strategy);
            when(strategy.matches("serviceValue", "userValue")).thenReturn(true);

            // When
            boolean result = service.matches(contextKey, "serviceValue", "userValue");

            // Then
            assertThat(result).isTrue();
            verify(strategy).matches("serviceValue", "userValue");
        }

        @Test
        @DisplayName("Should return false when strategy does not match values")
        void shouldReturnFalseWhenStrategyDoesNotMatchValues() {
            // Given
            String contextKey = "TEST_KEY";
            ContextResolveStrategy strategy = mock(ContextResolveStrategy.class);

            when(strategyRegistry.getStrategy(contextKey)).thenReturn(strategy);
            when(strategy.matches("serviceValue", "userValue")).thenReturn(false);

            // When
            boolean result = service.matches(contextKey, "serviceValue", "userValue");

            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("getAvailableContextKeys")
    class GetAvailableContextKeysTests {

        @Test
        @DisplayName("Should return all registered strategy keys")
        void shouldReturnAllRegisteredStrategyKeys() {
            // Given
            Map<String, ContextResolveStrategy> strategies = new HashMap<>();
            strategies.put("KEY1", mock(ContextResolveStrategy.class));
            strategies.put("KEY2", mock(ContextResolveStrategy.class));
            when(strategyRegistry.getAllStrategies()).thenReturn(strategies);

            // When
            Collection<String> result = service.getAvailableContextKeys();

            // Then
            assertThat(result).containsExactlyInAnyOrder("KEY1", "KEY2");
        }

        @Test
        @DisplayName("Should return empty collection when no strategies are registered")
        void shouldReturnEmptyCollectionWhenNoStrategiesAreRegistered() {
            // Given
            when(strategyRegistry.getAllStrategies()).thenReturn(Collections.emptyMap());

            // When
            Collection<String> result = service.getAvailableContextKeys();

            // Then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("hasStrategy")
    class HasStrategyTests {

        @Test
        @DisplayName("Should return true when strategy exists")
        void shouldReturnTrueWhenStrategyExists() {
            // Given
            String contextKey = "EXISTING_KEY";
            when(strategyRegistry.hasStrategy(contextKey)).thenReturn(true);

            // When
            boolean result = service.hasStrategy(contextKey);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Should return false when strategy does not exist")
        void shouldReturnFalseWhenStrategyDoesNotExist() {
            // Given
            String contextKey = "NON_EXISTING_KEY";
            when(strategyRegistry.hasStrategy(contextKey)).thenReturn(false);

            // When
            boolean result = service.hasStrategy(contextKey);

            // Then
            assertThat(result).isFalse();
        }
    }
}
