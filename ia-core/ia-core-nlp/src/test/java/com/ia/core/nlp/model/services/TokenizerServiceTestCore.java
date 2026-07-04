package com.ia.core.nlp.model.services;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para TokenizerService baseados nos casos de teste documentados.
 */
class TokenizerServiceTestCore extends CoreBaseUnitTest {

    @Test
    void deveImplementarCoreTokenizerService() {
        // Arrange & Act & Assert
        // Teste de verificação de interface - TokenizerService implementa CoreTokenizerService
        CoreTokenizerService service = new TokenizerService(null);
        assertThat(service).isInstanceOf(CoreTokenizerService.class);
    }
}
