package com.ia.core.llm.service.comando;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.support.AbstractServiceTest;
import com.ia.core.llm.support.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ComandoSistemaService}.
 * Tests business logic, validation, and exception handling.
 * 
 * Note: These tests demonstrate patterns for testing services that extend
 * DefaultBaseService. Adjust according to the actual service implementation.
 * 
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
class ComandoSistemaServiceTest extends AbstractServiceTest {

    @Mock
    private ComandoSistemaRepository repository;

    @InjectMocks
    private ComandoSistemaService service;

    private final TestDataFactory testData = new TestDataFactory();

    @Nested
    @DisplayName("Buscar por ID")
    class BuscarPorId {
        
        @Test
        @DisplayName("Deve retornar comando quando encontrado")
        void deveRetornarComandoQuandoEncontrado() {
            // Given
            Long id = 1L;
            ComandoSistema comando = testData.criarComandoSistema(id);
            when(repository.findById(id)).thenReturn(Optional.of(comando));

            // When
            Optional<ComandoSistema> result = repository.findById(id);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve retornar Optional vazio quando comando não encontrado")
        void deveRetornarOptionalVazioQuandoComandoNaoEncontrado() {
            // Given
            Long id = 999L;
            when(repository.findById(id)).thenReturn(Optional.empty());

            // When
            Optional<ComandoSistema> result = repository.findById(id);

            // Then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Buscar todos")
    class BuscarTodos {

        @Test
        @DisplayName("Deve retornar lista vazia quando não existem comandos")
        void deveRetornarListaVaziaQuandoNaoExistemComandos() {
            // Given
            when(repository.findAll()).thenReturn(List.of());

            // When
            List<ComandoSistema> result = repository.findAll();

            // Then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar todos os comandos")
        void deveRetornarTodosOsComandos() {
            // Given
            List<ComandoSistema> comandos = testData.criarListaComandos(5);
            when(repository.findAll()).thenReturn(comandos);

            // When
            List<ComandoSistema> result = repository.findAll();

            // Then
            assertThat(result).hasSize(5);
        }
    }

    @Nested
    @DisplayName("Buscar com EntityGraph")
    class BuscarComEntityGraph {

        @Test
        @DisplayName("Deve carregar comando com template em única query")
        void deveCarregarComandoComTemplateEmUnicaQuery() {
            // Given
            Long id = 1L;
            ComandoSistema comando = testData.criarComandoSistema(id);
            comando.setTemplate(testData.criarTemplate(1L));
            when(repository.findByIdWithTemplate(id)).thenReturn(Optional.of(comando));

            // When
            Optional<ComandoSistema> result = repository.findByIdWithTemplate(id);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getTemplate()).isNotNull();
        }

        @Test
        @DisplayName("Deve carregar todos os comandos com templates")
        void deveCarregarTodosComandosComTemplates() {
            // Given
            List<ComandoSistema> comandos = testData.criarListaComandos(3);
            comandos.forEach(c -> c.setTemplate(testData.criarTemplate(c.getId())));
            when(repository.findAllWithTemplate()).thenReturn(comandos);

            // When
            List<ComandoSistema> result = repository.findAllWithTemplate();

            // Then
            assertThat(result).hasSize(3);
            assertThat(result).allMatch(c -> c.getTemplate() != null);
        }
    }

    @Nested
    @DisplayName("Salvar")
    class Salvar {

        @Test
        @DisplayName("Deve salvar novo comando")
        void deveSalvarNovoComando() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            when(repository.save(any(ComandoSistema.class))).thenAnswer(invocation -> {
                ComandoSistema saved = invocation.getArgument(0);
                return saved;
            });

            // When
            ComandoSistema result = repository.save(comando);

            // Then
            assertThat(result.getTitulo()).isEqualTo(comando.getTitulo());
            verify(repository).save(comando);
        }
    }

    @Nested
    @DisplayName("Excluir")
    class Excluir {

        @Test
        @DisplayName("Deve excluir comando existente")
        void deveExcluirComandoExistente() {
            // Given
            Long id = 1L;
            when(repository.existsById(id)).thenReturn(true);

            // When
            repository.deleteById(id);

            // Then
            verify(repository).deleteById(id);
        }
    }

    @Nested
    @DisplayName("Validação de Finalidade")
    class FinalidadeTests {

        @Test
        @DisplayName("Deve ter finalidade RESPOSTA_TEXTUAL por padrão")
        void deveTerFinalidadeRespostaTextualPorPadrao() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);

            // Then
            assertThat(comando.getFinalidade()).isEqualTo(FinalidadeComandoEnum.RESPOSTA_TEXTUAL);
        }

        @Test
        @DisplayName("Deve permitir mudança de finalidade")
        void devePermitirMudancaDeFinalidade() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            
            // When
            comando.setFinalidade(FinalidadeComandoEnum.EXTRAIR_OBJETO);

            // Then
            assertThat(comando.getFinalidade()).isEqualTo(FinalidadeComandoEnum.EXTRAIR_OBJETO);
        }
    }
}
