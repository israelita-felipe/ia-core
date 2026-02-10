package com.ia.core.llm.service.comando;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.template.Template;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de performance para verificar eliminação de queries N+1.
 * 
 * @author Israel Araújo
 */
@DataJpaTest
@ActiveProfiles("test")
class ComandoSistemaRepositoryTest {

  @Autowired
  private ComandoSistemaRepository comandoSistemaRepository;

  private Template template;

  @BeforeEach
  void setUp() {
    // Cleanup
    comandoSistemaRepository.deleteAll();
    
    // Create template
    template = Template.builder()
        .titulo("Test Template")
        .conteudo("Test content")
        .exigeContexto(false)
        .build();
  }

  @Test
  @DisplayName("Deve carregar comando com template em única query usando EntityGraph")
  void deveCarregarComandoComTemplateEmUnicaQuery() {
    // Given
    ComandoSistema comando = ComandoSistema.builder()
        .titulo("Test Command")
        .comando("Test command content")
        .template(template)
        .build();
    comandoSistemaRepository.save(comando);

    // When - Using EntityGraph method
    Optional<ComandoSistema> result = comandoSistemaRepository.findByIdWithTemplate(comando.getId());

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getTemplate()).isNotNull();
    assertThat(result.get().getTemplate().getTitulo()).isEqualTo("Test Template");
  }

  @Test
  @DisplayName("Deve carregar todos os comandos com templates em única query usando EntityGraph")
  void deveCarregarTodosComandosComTemplatesEmUnicaQuery() {
    // Given
    for (int i = 0; i < 5; i++) {
      ComandoSistema comando = ComandoSistema.builder()
          .titulo("Test Command " + i)
          .comando("Test command content " + i)
          .template(template)
          .build();
      comandoSistemaRepository.save(comando);
    }

    // When - Using EntityGraph method
    List<ComandoSistema> result = comandoSistemaRepository.findAllWithTemplate();

    // Then
    assertThat(result).hasSize(5);
    assertThat(result).allMatch(c -> c.getTemplate() != null);
  }
}
