package com.ia.core.quartz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.model.periodicidade.Recorrencia;
import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.support.AbstractQuartzRepositoryIT;

/**
 * Testes de integração para o repositório de configuração de agendamento.
 *
 * @author Israel Araújo
 */
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
class SchedulerConfigRepositoryIT
  extends AbstractQuartzRepositoryIT {

  @Autowired
  private SchedulerConfigRepository repository;

  private Periodicidade periodicidade;

  @BeforeEach
  void setUp() {
    // Limpar dados completamente
    entityManager.createQuery("DELETE FROM SchedulerConfig")
        .executeUpdate();
    entityManager.createQuery("DELETE FROM Periodicidade").executeUpdate();
    entityManager.flush();
    entityManager.clear();
    periodicidade = criarPeriodicidade();
  }

  @Test
  @DisplayName("Deve persistir configuração de agendamento com sucesso")
  void devePersistirConfiguracaoDeAgendamentoComSucesso() {
    // Dado
    SchedulerConfig config = criarSchedulerConfig("com.example.TestJob",
                                                  true);

    // Quando
    SchedulerConfig salvo = persistAndFlush(config);

    // Então
    assertThat(salvo.getId()).isNotNull();
    assertThat(salvo.getJobClassName()).isEqualTo("com.example.TestJob");
    assertThat(salvo.getPeriodicidade()).isNotNull();
  }

  @Test
  @DisplayName("Deve encontrar configuração por ID")
  void deveEncontrarConfiguracaoPorId() {
    // Dado
    SchedulerConfig config = criarSchedulerConfig("com.example.TestJob",
                                                  true);
    SchedulerConfig salvo = persistAndFlush(config);
    flushAndClear();

    // Quando
    Optional<SchedulerConfig> resultado = repository
        .findById(salvo.getId());

    // Então
    assertThat(resultado).isPresent();
    assertThat(resultado.get().getJobClassName())
        .isEqualTo("com.example.TestJob");
  }

  @Test
  @DisplayName("Deve buscar todos ativos com periodicidade carregada")
  void deveBuscarTodosAtivosComPeriodicidadeCarregada() {
    // Dado
    persistAndFlush(criarSchedulerConfig("com.example.Job1", true));
    persistAndFlush(criarSchedulerConfig("com.example.Job2", true));
    persistAndFlush(criarSchedulerConfig("com.example.Job3", false));
    flushAndClear();

    // Quando
    List<SchedulerConfig> resultado = repository
        .findAllActiveWithPeriodicidade(true);

    // Então
    assertThat(resultado).hasSize(2);
  }

  @Test
  @DisplayName("Deve retornar projeções resumidas")
  void deveRetornarProjecoesResumidas() {
    // Dado
    persistAndFlush(criarSchedulerConfig("com.example.Job1", true));
    persistAndFlush(criarSchedulerConfig("com.example.Job2", true));
    flushAndClear();

    // Quando
    List<SchedulerConfig> resultado = repository.findAllSummaries();

    // Então
    assertThat(resultado).hasSize(2);
  }

  @Test
  @DisplayName("Deve deletar configuração de agendamento")
  void deveDeletarConfiguracaoDeAgendamento() {
    // Dado
    SchedulerConfig config = criarSchedulerConfig("com.example.TestJob",
                                                  true);
    SchedulerConfig salvo = persistAndFlush(config);
    flushAndClear();

    // Quando
    repository.delete(salvo);
    flushAndClear();

    // Então
    Optional<SchedulerConfig> resultado = repository
        .findById(salvo.getId());
    assertThat(resultado).isEmpty();
  }

  /**
   * Cria uma configuração de agendamento para teste.
   */
  private SchedulerConfig criarSchedulerConfig(String jobClassName,
                                               boolean ativo) {
    // Criar nova Periodicidade para cada SchedulerConfig
    Periodicidade novaPeriodicidade = criarPeriodicidade();
    novaPeriodicidade.setAtivo(ativo);

    return SchedulerConfig.builder().jobClassName(jobClassName)
        .periodicidade(novaPeriodicidade).build();
  }

  /**
   * Cria uma periodicidade para teste.
   */
  private Periodicidade criarPeriodicidade() {
    // Cria intervalo base com data e hora
    IntervaloTemporal intervalo = new IntervaloTemporal(java.time.LocalDate
        .now(), java.time.LocalTime.of(10, 0), java.time.LocalDate.now(),
                                                        java.time.LocalTime
                                                            .of(11, 0));

    // Cria regra de recorrência semanal
    Recorrencia recorrencia = new Recorrencia();
    recorrencia.setFrequency(Frequencia.SEMANALMENTE);
    recorrencia.setIntervalValue(1);

    // Cria periodicidade
    Periodicidade periodo = Periodicidade.builder().ativo(true)
        .zoneId(ZoneId.of("America/Recife").getId())
        .intervaloBase(intervalo).regra(recorrencia).build();

    return periodo;
  }
}
