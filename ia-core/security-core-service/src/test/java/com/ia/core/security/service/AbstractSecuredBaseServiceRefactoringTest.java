package com.ia.core.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.strategy.ContextResolveStrategy;
import com.ia.core.security.service.strategy.ContextResolveStrategyRegistry;
import com.ia.core.security.service.strategy.impl.IdContextResolveStrategy;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Testes para validar a refatoração de AbstractSecuredBaseService. Objetivo:
 * Garantir que a segregação de responsabilidades mantém funcionalidade e
 * permite extensão via Strategy Pattern.
 *
 * @author GitHub Copilot
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Refatoração - AbstractSecuredBaseService")
class AbstractSecuredBaseServiceRefactoringTest {

  @Mock
  private CoreSecurityAuthorizationManager authorizationManager;

  @Mock
  private BaseEntityRepository<?> repository;

  private ContextResolveStrategyRegistry strategyRegistry;

  private SecurityContextService contextService;

  @BeforeEach
  void setUp() {
    // Setup AuthorizationService

    // Setup ContextResolveStrategy
    IdContextResolveStrategy idStrategy = new IdContextResolveStrategy();
    strategyRegistry = new ContextResolveStrategyRegistry(List
        .of(idStrategy));

    // Setup SecurityContextService
    contextService = new SecurityContextService(strategyRegistry);
  }

  @Test
  @DisplayName("ContextResolveStrategyRegistry deve registrar estratégias automaticamente")
  void testContextResolveStrategyRegistryRegistration() {
    assertTrue(strategyRegistry.hasStrategy("ID"));
    assertNotNull(strategyRegistry.getStrategy("ID"));
  }

  @Test
  @DisplayName("ContextResolveStrategyRegistry deve retornar null para chave desconhecida")
  void testContextResolveStrategyRegistryUnknownKey() {
    assertFalse(strategyRegistry.hasStrategy("UNKNOWN"));
    assertNull(strategyRegistry.getStrategy("UNKNOWN"));
  }

  @Test
  @DisplayName("IdContextResolveStrategy deve ter chave correta")
  void testIdContextResolveStrategyKey() {
    ContextResolveStrategy strategy = strategyRegistry.getStrategy("ID");
    assertNotNull(strategy);
    assertEquals("ID", strategy.getContextKey());
  }

  @Test
  @DisplayName("IdContextResolveStrategy deve validar canHandle")
  void testIdContextResolveStrategyCanHandle() {
    ContextResolveStrategy strategy = strategyRegistry.getStrategy("ID");
    assertNotNull(strategy);
    assertTrue(strategy.canHandle("ID"));
    assertFalse(strategy.canHandle("ROLE"));
  }

  @Test
  @DisplayName("SecurityContextService deve resolver contexto via estratégia")
  void testSecurityContextServiceResolveContext() {
    Collection<Object> result = contextService
        .resolveContextValues("ID", List.of("1", "2"), repository);
    assertNotNull(result);
    // Resultado depende do mock do repository
  }

  @Test
  @DisplayName("SecurityContextService deve validar correspondência de contexto")
  void testSecurityContextServiceMatches() {
    // JSON com lista de IDs
    String serviceValue = "[\"1\", \"2\", \"3\"]";
    String userValue = "2";

    boolean matches = contextService.matches("ID", serviceValue, userValue);
    assertTrue(matches);
  }

  @Test
  @DisplayName("SecurityContextService deve retornar false para não correspondência")
  void testSecurityContextServiceNoMatches() {
    String serviceValue = "[\"1\", \"2\", \"3\"]";
    String userValue = "99";

    boolean matches = contextService.matches("ID", serviceValue, userValue);
    assertFalse(matches);
  }

  @Test
  @DisplayName("SecurityContextService deve listar estratégias disponíveis")
  void testSecurityContextServiceAvailableKeys() {
    Collection<String> keys = contextService.getAvailableContextKeys();
    assertNotNull(keys);
    assertTrue(keys.contains("ID"));
  }

  @Test
  @DisplayName("SecurityContextService deve indicar existência de estratégia")
  void testSecurityContextServiceHasStrategy() {
    assertTrue(contextService.hasStrategy("ID"));
    assertFalse(contextService.hasStrategy("ROLE"));
  }

  @Test
  @DisplayName("SecurityContextService deve validar parâmetros não nulos")
  void testSecurityContextServiceNullValidation() {
    assertThrows(NullPointerException.class, () -> contextService
        .resolveContextValues(null, List.of(), repository));

    assertThrows(NullPointerException.class, () -> contextService
        .resolveContextValues("ID", null, repository));

    assertThrows(NullPointerException.class,
                 () -> contextService.matches(null, "value", "value"));
  }
}
