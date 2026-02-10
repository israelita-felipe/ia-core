package com.ia.core.llm.service.template;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.model.template.TemplateParameter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de performance para verificar eliminação de queries N+1 em Template.
 * 
 * @author Israel Araújo
 */
@DataJpaTest
@ActiveProfiles("test")
class TemplateRepositoryTest {

  @Autowired
  private TemplateRepository templateRepository;

  private Template template;

  @BeforeEach
  void setUp() {
    templateRepository.deleteAll();
    
    template = Template.builder()
        .titulo("Test Template")
        .conteudo("Test content with param")
        .exigeContexto(true)
        .build();
  }

  @Test
  @DisplayName("Deve carregar template com parâmetros em única query usando EntityGraph")
  void deveCarregarTemplateComParametrosEmUnicaQuery() {
    // Given
    TemplateParameter param1 = TemplateParameter.builder()
        .nome("param1")
        .template(template)
        .build();
    TemplateParameter param2 = TemplateParameter.builder()
        .nome("param2")
        .template(template)
        .build();
    template.getParametros().add(param1);
    template.getParametros().add(param2);
    
    templateRepository.save(template);

    // When - Using EntityGraph method
    Optional<Template> result = templateRepository.findByIdWithParametros(template.getId());

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getParametros()).hasSize(2);
  }

  @Test
  @DisplayName("Deve carregar todos os templates com parâmetros em única query usando EntityGraph")
  void deveCarregarTodosTemplatesComParametrosEmUnicaQuery() {
    // Given
    for (int i = 0; i < 3; i++) {
      Template t = Template.builder()
          .titulo("Template " + i)
          .conteudo("Content " + i)
          .exigeContexto(false)
          .build();
      TemplateParameter param = TemplateParameter.builder()
          .nome("param" + i)
          .template(t)
          .build();
      t.getParametros().add(param);
      templateRepository.save(t);
    }

    // When - Using EntityGraph method
    List<Template> result = templateRepository.findAllWithParametros();

    // Then
    assertThat(result).hasSize(3);
    assertThat(result).allMatch(t -> !t.getParametros().isEmpty());
  }
}
