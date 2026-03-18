package com.ia.core.quartz.model.periodicidade.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.support.AbstractQuartzRepositoryIT;

import lombok.extern.slf4j.Slf4j;

/**
 * Testes de integração para {@link PeriodicidadeRepository}.
 * <p>
 * Valida os métodos de consulta especializados para periodicidades,
 * incluindo busca por status ativo, por período de datas e por
 * existência de regras de recorrência.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
class PeriodicidadeRepositoryIT extends AbstractQuartzRepositoryIT {

  @Autowired
  private PeriodicidadeRepository repository;

  @Test
  @DisplayName("findByAtivoTrue deve retornar apenas periodicidades ativas")
  void findByAtivoTrue_deveRetornarApenasPeriodicidadesAtivas() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    criarPeriodicidadeAtiva(dataInicio);
    criarPeriodicidadeInativa(dataInicio.plusDays(1));
    criarPeriodicidadeAtiva(dataInicio.plusDays(2));
    criarPeriodicidadeInativa(dataInicio.plusDays(3));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findByAtivoTrue();

    // Assert
    assertThat(result).hasSize(2);
    assertThat(result).allMatch(p -> p.getAtivo());
  }

  @Test
  @DisplayName("findByAtivoTrue deve retornar lista vazia quando não há periodicidades ativas")
  void findByAtivoTrue_deveRetornarListaVaziaQuandoNaoHaPeriodicidadesAtivas() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    criarPeriodicidadeInativa(dataInicio);
    criarPeriodicidadeInativa(dataInicio.plusDays(1));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findByAtivoTrue();

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("findAtivasAteData deve retornar periodicidades ativas que iniciaram até a data")
  void findAtivasAteData_deveRetornarPeriodicidadesAtivasAteData() {
    // Arrange
    LocalDate dataReferencia = LocalDate.of(2024, 1, 15);
    Periodicidade p1 = criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 10));
    Periodicidade p2 = criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 20)); // Após a data de referência
    Periodicidade p3 = criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 15)); // Na data de referência
    Periodicidade p4 = criarPeriodicidadeInativa(LocalDate.of(2024, 1, 5)); // Inativa
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasAteData(dataReferencia);

    // Assert
    assertThat(result).hasSize(2);
    assertThat(result).extracting(Periodicidade::getId)
        .containsExactlyInAnyOrder(p1.getId(), p3.getId());
  }

  @Test
  @DisplayName("findAtivasAteData deve considerar apenas ativos")
  void findAtivasAteData_deveConsiderarApenasAtivos() {
    // Arrange
    LocalDate dataReferencia = LocalDate.of(2024, 1, 15);
    criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 10));
    criarPeriodicidadeInativa(LocalDate.of(2024, 1, 5));
    criarPeriodicidadeInativa(LocalDate.of(2024, 1, 12));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasAteData(dataReferencia);

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getAtivo()).isTrue();
  }

  @Test
  @DisplayName("findAllWithRecurrence deve retornar periodicidades com regra de recorrência")
  void findAllWithRecurrence_deveRetornarPeriodicidadesComRegra() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    Periodicidade comRegra = criarPeriodicidade(Boolean.TRUE, dataInicio, null,
        com.ia.core.quartz.model.periodicidade.Frequencia.SEMANALMENTE);
    criarPeriodicidadeSemRegra(Boolean.TRUE, dataInicio.plusDays(1));
    criarPeriodicidadeSemRegra(Boolean.TRUE, dataInicio.plusDays(2));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAllWithRecurrence();

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(comRegra.getId());
  }

  @Test
  @DisplayName("findAllWithRecurrence deve retornar lista vazia quando nenhuma periodicidade tem regra")
  void findAllWithRecurrence_deveRetornarListaVaziaQuandoNenhumaTemRegra() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    criarPeriodicidadeSemRegra(Boolean.TRUE, dataInicio);
    criarPeriodicidadeSemRegra(Boolean.TRUE, dataInicio.plusDays(1));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAllWithRecurrence();

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("findAllWithRecurrence deve ignorar periodicidades com regra nula")
  void findAllWithRecurrence_deveIgnorarPeriodicidadesComRegraNula() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    Periodicidade comRegra = criarPeriodicidade(Boolean.TRUE, dataInicio, null,
        com.ia.core.quartz.model.periodicidade.Frequencia.MENSALMENTE);

    Periodicidade semRegra = Periodicidade.builder()
        .ativo(Boolean.TRUE)
        .build();
    semRegra.getIntervaloBase().setStartDate(dataInicio.plusDays(1));
    entityManager.persist(semRegra);
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAllWithRecurrence();

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(comRegra.getId());
  }

  @Test
  @DisplayName("findAtivasBetweenDates deve retornar periodicidades ativas no período")
  void findAtivasBetweenDates_deveRetornarPeriodicidadesAtivasNoPeriodo() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 31);

    Periodicidade p1 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2024, 1, 10), null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
    Periodicidade p2 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2024, 2, 1), null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE); // Após o período
    Periodicidade p3 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2023, 12, 15), LocalDate.of(2024, 1, 15),
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE); // Dentro do período com data fim
    Periodicidade p4 = criarPeriodicidadeInativa(LocalDate.of(2024, 1, 20)); // Inativa
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasBetweenDates(startDate, endDate);

    // Assert
    assertThat(result).hasSize(2);
    assertThat(result).extracting(Periodicidade::getId)
        .containsExactlyInAnyOrder(p1.getId(), p3.getId());
  }

  @Test
  @DisplayName("findAtivasBetweenDates deve incluir periodicidades sem data fim")
  void findAtivasBetweenDates_deveIncluirPeriodicidadesSemDataFim() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 31);

    Periodicidade p1 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2024, 1, 10), null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
    Periodicidade p2 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 20),
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasBetweenDates(startDate, endDate);

    // Assert
    assertThat(result).hasSize(2);
  }

  @Test
  @DisplayName("findAtivasBetweenDates deve ignorar periodicidades que terminaram antes do período")
  void findAtivasBetweenDates_deveIgnorarPeriodicidadesTerminadasAntes() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 31);

    Periodicidade p1 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 31),
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
    Periodicidade p2 = criarPeriodicidade(Boolean.TRUE,
        LocalDate.of(2024, 1, 10), null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasBetweenDates(startDate, endDate);

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(p2.getId());
  }

  @Test
  @DisplayName("findAtivasBetweenDates deve ignorar periodicidades inativas")
  void findAtivasBetweenDates_deveIgnorarPeriodicidadesInativas() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 31);

    criarPeriodicidadeInativa(LocalDate.of(2024, 1, 10));
    Periodicidade p2 = criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 15));
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasBetweenDates(startDate, endDate);

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(p2.getId());
  }

  @Test
  @DisplayName("findAtivasBetweenDates deve retornar lista vazia para período sem periodicidades")
  void findAtivasBetweenDates_deveRetornarListaVaziaParaPeriodoSemPeriodicidades() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 1, 31);

    criarPeriodicidadeAtiva(LocalDate.of(2024, 2, 15)); // Fora do período
    limparContexto();

    // Act
    List<Periodicidade> result = repository.findAtivasBetweenDates(startDate, endDate);

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("save deve persistir periodicidade com todos os campos")
  void save_devePersistirPeriodicidadeComTodosOsCampos() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2024, 1, 1);
    LocalDate dataFim = LocalDate.of(2024, 12, 31);

    Periodicidade periodicidade = Periodicidade.builder()
        .ativo(Boolean.TRUE)
        .zoneId("America/Sao_Paulo")
        .build();

    periodicidade.getIntervaloBase().setStartDate(dataInicio);
    periodicidade.getIntervaloBase().setEndDate(dataFim);

    com.ia.core.quartz.model.periodicidade.Recorrencia regra = 
        new com.ia.core.quartz.model.periodicidade.Recorrencia();
    regra.setFrequency(com.ia.core.quartz.model.periodicidade.Frequencia.SEMANALMENTE);
    periodicidade.setRegra(regra);

    // Act
    Periodicidade salva = repository.save(periodicidade);
    limparContexto();

    // Assert
    assertThat(salva.getId()).isNotNull();
    Periodicidade recuperada = repository.findById(salva.getId()).orElseThrow();
    assertThat(recuperada.getAtivo()).isTrue();
    assertThat(recuperada.getZoneId()).isEqualTo("America/Sao_Paulo");
    assertThat(recuperada.getIntervaloBase().getStartDate()).isEqualTo(dataInicio);
    assertThat(recuperada.getIntervaloBase().getEndDate()).isEqualTo(dataFim);
    assertThat(recuperada.getRegra().getFrequency())
        .isEqualTo(com.ia.core.quartz.model.periodicidade.Frequencia.SEMANALMENTE);
  }

  @Test
  @DisplayName("delete deve remover periodicidade")
  void delete_deveRemoverPeriodicidade() {
    // Arrange
    Periodicidade periodicidade = criarPeriodicidadeAtiva(LocalDate.of(2024, 1, 1));
    Long id = periodicidade.getId();
    limparContexto();

    // Act
    repository.delete(periodicidade);
    limparContexto();

    // Assert
    assertThat(repository.findById(id)).isEmpty();
  }
}
