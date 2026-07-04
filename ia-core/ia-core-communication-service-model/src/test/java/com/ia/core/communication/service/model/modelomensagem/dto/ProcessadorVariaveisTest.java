package com.ia.core.communication.service.model.modelomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ProcessadorVariaveis.
 */
@DisplayName("Testes de ProcessadorVariaveis")
class ProcessadorVariaveisTest {

  private final ProcessadorVariaveis processador = new ProcessadorVariaveis();

  @Test
  @DisplayName("Deve processar template com variáveis")
  void deveProcessarTemplateComVariaveis() {
    String template = "Olá {{nome}}, bem-vindo!";
    Map<Variavel, Object> contexto = Map.of(VariavelTemplate.NOME, "João");

    String resultado = processador.processar(template, contexto);

    assertThat(resultado).isEqualTo("Olá João, bem-vindo!");
  }

  @Test
  @DisplayName("Deve retornar string vazia quando template é nulo")
  void deveRetornarStringVaziaQuandoTemplateENulo() {
    String resultado = processador.processar(null, Map.of());

    assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("Deve retornar string vazia quando template é vazio")
  void deveRetornarStringVaziaQuandoTemplateEVazio() {
    String resultado = processador.processar("", Map.of());

    assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("Deve manter texto sem variáveis")
  void deveManterTextoSemVariaveis() {
    String template = "Olá, bem-vindo!";

    String resultado = processador.processar(template, Map.of());

    assertThat(resultado).isEqualTo("Olá, bem-vindo!");
  }

  @Test
  @DisplayName("Deve substituir múltiplas variáveis")
  void deveSubstituirMultiplasVariaveis() {
    String template = "Olá {{nome}}, seu telefone é {{telefone}} e e-mail {{email}}";
    Map<Variavel, Object> contexto = Map.of(
        VariavelTemplate.NOME, "João",
        VariavelTemplate.TELEFONE, "11999999999",
        VariavelTemplate.EMAIL, "joao@email.com"
    );

    String resultado = processador.processar(template, contexto);

    assertThat(resultado).isEqualTo("Olá João, seu telefone é 11999999999 e e-mail joao@email.com");
  }

  @Test
  @DisplayName("Deve retornar string vazia para variável não encontrada")
  void deveRetornarStringVaziaParaVariavelNaoEncontrada() {
    String template = "Olá {{nome}}";
    Map<Variavel, Object> contexto = Map.of();

    String resultado = processador.processar(template, contexto);

    assertThat(resultado).isEqualTo("Olá ");
  }

  @Test
  @DisplayName("Deve listar chaves disponíveis")
  void deveListarChavesDisponiveis() {
    var chaves = processador.listarChavesDisponiveis();

    assertThat(chaves).isNotEmpty();
    assertThat(chaves).contains("{{nome}}");
    assertThat(chaves).contains("{{telefone}}");
  }
}
