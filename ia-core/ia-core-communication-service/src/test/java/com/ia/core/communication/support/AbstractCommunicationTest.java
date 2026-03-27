package com.ia.core.communication.support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Classe base para testes de unidades do módulo de comunicação.
 * Fornece configurações comuns e utilitários para testes de serviços.
 *
 * Características:
 * - Extensão Mockito para mocking
 * - Utilitários comuns de teste
 * - Suporte a logging
 *
 * Uso:
 * <pre>
 * {@code @ExtendWith(MockitoExtension.class)}
 * class MyServiceTest extends AbstractCommunicationTest {
 *     // Seus métodos de teste
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
public abstract class AbstractCommunicationTest {

    /**
     * Cria uma mensagem de teste para propósitos de exibição.
     *
     * @param testName o nome do teste
     * @return mensagem de teste formatada
     */
    protected String testMessage(String testName) {
        return String.format("[TEST] %s - %s",
            this.getClass().getSimpleName(), testName);
    }

    /**
     * Verifica que uma mensagem de exceção contém o texto esperado.
     *
     * @param message a mensagem da exceção
     * @param expectedText o texto esperado
     * @return true se a mensagem contém o texto esperado
     */
    protected boolean messageContains(String message, String expectedText) {
        return message != null && message.contains(expectedText);
    }

    /**
     * Gera um e-mail de teste aleatório.
     *
     * @return e-mail de teste
     */
    protected String generateTestEmail() {
        return "test" + System.currentTimeMillis() + "@example.com";
    }

    /**
     * Gera um assunto de teste aleatório.
     *
     * @return assunto de teste
     */
    protected String generateTestSubject() {
        return "Test Subject - " + System.currentTimeMillis();
    }

    /**
     * Gera um corpo de mensagem de teste aleatório.
     *
     * @return corpo de mensagem de teste
     */
    protected String generateTestBody() {
        return "This is a test message body sent at " + System.currentTimeMillis();
    }
}
