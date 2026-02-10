package com.ia.core.llm.service.comando;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.support.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ComandoSistemaRepository}.
 * Uses HSQLDB in-memory database for fast testing.
 * 
 * @author Israel Araújo
 */
@DataJpaTest
@ActiveProfiles("test")
class ComandoSistemaRepositoryIT {

    @Autowired
    private ComandoSistemaRepository repository;

    private TestDataFactory testData;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        testData = new TestDataFactory();
    }

    @Nested
    @DisplayName("CRUD Operations")
    class CrudOperations {

        @Test
        @DisplayName("Deve salvar comando com todas as informações")
        void deveSalvarComandoComTodasInformacoes() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);

            // When
            ComandoSistema salvo = repository.save(comando);

            // Then
            assertThat(salvo.getId()).isNotNull();
            assertThat(salvo.getTitulo()).isEqualTo(comando.getTitulo());
            assertThat(salvo.getComando()).isEqualTo(comando.getComando());
            assertThat(salvo.getFinalidade()).isEqualTo(FinalidadeComandoEnum.RESPOSTA_TEXTUAL);
        }

        @Test
        @DisplayName("Deve encontrar comando por ID")
        void deveEncontrarComandoPorId() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            repository.save(comando);

            // When
            Optional<ComandoSistema> result = repository.findById(comando.getId());

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getTitulo()).isEqualTo(comando.getTitulo());
        }

        @Test
        @DisplayName("Deve retornar Optional vazio para ID inexistente")
        void deveRetornarOptionalVazioParaIDInexistente() {
            // When
            Optional<ComandoSistema> result = repository.findById(999L);

            // Then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Deve atualizar comando existente")
        void deveAtualizarComandoExistente() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            ComandoSistema salvo = repository.save(comando);

            // When
            salvo.setTitulo("Título Atualizado");
            salvo.setComando("Comando Atualizado");
            repository.save(salvo);

            // Then
            Optional<ComandoSistema> result = repository.findById(salvo.getId());
            assertThat(result).isPresent();
            assertThat(result.get().getTitulo()).isEqualTo("Título Atualizado");
        }

        @Test
        @DisplayName("Deve excluir comando existente")
        void deveExcluirComandoExistente() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            ComandoSistema salvo = repository.save(comando);

            // When
            repository.deleteById(salvo.getId());

            // Then
            assertThat(repository.findById(salvo.getId())).isEmpty();
        }
    }

    @Nested
    @DisplayName("EntityGraph Performance Tests")
    class EntityGraphTests {

        @Test
        @DisplayName("Deve carregar comando com template em única query usando EntityGraph")
        void deveCarregarComandoComTemplateEmUnicaQuery() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            comando.setTemplate(testData.criarTemplate(1L));
            repository.save(comando);

            // When - Using EntityGraph method
            Optional<ComandoSistema> result = repository.findByIdWithTemplate(comando.getId());

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getTemplate()).isNotNull();
        }

        @Test
        @DisplayName("Deve carregar todos os comandos com templates em única query usando EntityGraph")
        void deveCarregarTodosComandosComTemplatesEmUnicaQuery() {
            // Given
            for (int i = 0; i < 5; i++) {
                ComandoSistema comando = testData.criarComandoSistema((long) i);
                comando.setTemplate(testData.criarTemplate((long) i));
                repository.save(comando);
            }

            // When - Using EntityGraph method
            List<ComandoSistema> result = repository.findAllWithTemplate();

            // Then
            assertThat(result).hasSize(5);
            assertThat(result).allMatch(c -> c.getTemplate() != null);
        }
    }

    @Nested
    @DisplayName("Query Methods")
    class QueryMethods {

        @Test
        @DisplayName("Deve encontrar todos os comandos")
        void deveEncontrarTodosOsComandos() {
            // Given
            for (int i = 0; i < 3; i++) {
                repository.save(testData.criarComandoSistema((long) i));
            }

            // When
            List<ComandoSistema> result = repository.findAll();

            // Then
            assertThat(result).hasSize(3);
        }

        @Test
        @DisplayName("Deve verificar existência por ID")
        void deveVerificarExistenciaPorId() {
            // Given
            ComandoSistema comando = testData.criarComandoSistema(1L);
            repository.save(comando);

            // When & Then
            assertThat(repository.existsById(comando.getId())).isTrue();
            assertThat(repository.existsById(999L)).isFalse();
        }
    }
}
