package com.ia.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Testes para os métodos default de {@link HasTransaction}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HasTransaction")
class HasTransactionTest {

    @Mock
    private PlatformTransactionManager transactionManager;

    private TestHasTransaction service;

    @BeforeEach
    void setUp() {
        service = new TestHasTransaction(transactionManager);
    }

    @Nested
    @DisplayName("createTransactionDefinition")
    class TestesCreateTransactionDefinition {

        @Test
        @DisplayName("Deve criar definição de transação com readOnly true")
        void deveCriarDefinicaoDeTransacaoComReadOnlyTrue() {
            // Quando
            TransactionDefinition result = service.createTransactionDefinition(true);

            // Então
            assertThat(result).isInstanceOf(DefaultTransactionDefinition.class);
            assertThat(result.isReadOnly()).isTrue();
        }

        @Test
        @DisplayName("Deve criar definição de transação com readOnly false")
        void deveCriarDefinicaoDeTransacaoComReadOnlyFalse() {
            // Quando
            TransactionDefinition result = service.createTransactionDefinition(false);

            // Então
            assertThat(result).isInstanceOf(DefaultTransactionDefinition.class);
            assertThat(result.isReadOnly()).isFalse();
        }

        @Test
        @DisplayName("Deve definir comportamento de propagação como required")
        void deveDefinirComportamentoDePropagacaoComoRequired() {
            // Quando
            TransactionDefinition result = service.createTransactionDefinition(false);

            // Então
            assertThat(result.getPropagationBehavior())
                .isEqualTo(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        }

        @Test
        @DisplayName("Deve definir nível de isolamento como read uncommitted")
        void deveDefinirNivelDeIsolamentoComoReadUncommitted() {
            // Quando
            TransactionDefinition result = service.createTransactionDefinition(false);

            // Então
            assertThat(result.getIsolationLevel())
                .isEqualTo(DefaultTransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        }
    }

    @Nested
    @DisplayName("onTransaction")
    class TestesOnTransaction {

        @Test
        @DisplayName("Deve executar ação dentro de transação e fazer commit")
        void deveExecutarAcaoDentroDeTransacaoEFazerCommit() {
            // Dado
            TransactionStatus status = mock(TransactionStatus.class);
            when(status.isNewTransaction()).thenReturn(true);
            when(status.isReadOnly()).thenReturn(false);
            when(transactionManager.getTransaction(any(TransactionDefinition.class)))
                .thenReturn(status);
            doNothing().when(transactionManager).commit(status);

            Supplier<String> action = () -> "resultado";

            // Quando
            String result = service.onTransaction(action);

            // Então
            assertThat(result).isEqualTo("resultado");
            verify(transactionManager).getTransaction(any(TransactionDefinition.class));
            verify(transactionManager).commit(status);
        }

        @Test
        @DisplayName("Deve executar ação dentro de transação somente leitura")
        void deveExecutarAcaoDentroDeTransacaoSomenteLeitura() {
            // Dado
            TransactionStatus status = mock(TransactionStatus.class);
            when(status.isNewTransaction()).thenReturn(true);
            when(status.isReadOnly()).thenReturn(true);
            when(transactionManager.getTransaction(any(TransactionDefinition.class)))
                .thenReturn(status);

            Supplier<String> action = () -> "resultado";

            // Quando
            String result = service.onTransaction(true, action);

            // Então
            assertThat(result).isEqualTo("resultado");
            verify(transactionManager).getTransaction(any(TransactionDefinition.class));
        }

        @Test
        @DisplayName("Deve fazer rollback em caso de exceção")
        void deveFazerRollbackEmCasoDeExcecao() {
            // Dado
            TransactionStatus status = mock(TransactionStatus.class);
            when(status.isNewTransaction()).thenReturn(true);
            when(transactionManager.getTransaction(any(TransactionDefinition.class)))
                .thenReturn(status);
            doNothing().when(transactionManager).rollback(status);

            Supplier<String> action = () -> {
                throw new RuntimeException("Exceção de teste");
            };

            // Quando & Então
            assertThatThrownBy(() -> service.onTransaction(action))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Exceção de teste");
            verify(transactionManager).getTransaction(any(TransactionDefinition.class));
            verify(transactionManager).rollback(status);
        }

        @Test
        @DisplayName("Deve fazer commit de transação somente leitura")
        void deveFazerCommitDeTransacaoSomenteLeitura() {
            // Dado
            TransactionStatus status = mock(TransactionStatus.class);
            when(status.isNewTransaction()).thenReturn(true);
            when(status.isReadOnly()).thenReturn(true);
            when(transactionManager.getTransaction(any(TransactionDefinition.class)))
                .thenReturn(status);

            Supplier<String> action = () -> "resultado";

            // Quando
            String result = service.onTransaction(action);

            // Então
            assertThat(result).isEqualTo("resultado");
            verify(transactionManager).getTransaction(any(TransactionDefinition.class));
        }

        @Test
        @DisplayName("Deve chamar onTransaction com readOnly false por padrão")
        void deveChamarOnTransactionComReadOnlyFalsePorPadrao() {
            // Dado
            TransactionStatus status = mock(TransactionStatus.class);
            when(status.isNewTransaction()).thenReturn(true);
            when(status.isReadOnly()).thenReturn(false);
            when(transactionManager.getTransaction(any(TransactionDefinition.class)))
                .thenReturn(status);
            doNothing().when(transactionManager).commit(status);

            Supplier<String> action = () -> "resultado";

            // Quando
            String result = service.onTransaction(action);

            // Então
            assertThat(result).isEqualTo("resultado");
            verify(transactionManager).getTransaction(any(TransactionDefinition.class));
            verify(transactionManager).commit(status);
        }
    }

    // Implementação de teste
    static class TestHasTransaction implements HasTransaction {
        private final PlatformTransactionManager transactionManager;

        TestHasTransaction(PlatformTransactionManager transactionManager) {
            this.transactionManager = transactionManager;
        }

        @Override
        public PlatformTransactionManager getTransactionManager() {
            return transactionManager;
        }
    }
}
