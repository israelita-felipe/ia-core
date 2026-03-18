package com.ia.core.quartz.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.model.periodicidade.Recorrencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Classe base para testes de integração do módulo quartz.
 * <p>
 * Configura um ambiente de testes com banco de dados H2 em memória
 * e fornece métodos utilitários para criação de entidades de teste.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class AbstractQuartzRepositoryIT {

  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  protected DataSource dataSource;

  /**
   * Configuração do banco de dados para testes.
   */
  @Configuration
  static class TestConfig {

    @Bean
    public DataSource dataSource() {
      EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
          .setType(EmbeddedDatabaseType.H2)
          .setName("testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL;SCHEMA=QUARTZ")
          .build();

      // Criar schema QUARTZ
      try (Connection conn = db.getConnection()) {
        conn.createStatement().execute("CREATE SCHEMA IF NOT EXISTS QUARTZ");
      } catch (SQLException e) {
        // Schema pode já existir
      }

      return db;
    }
  }

  /**
   * Cria e persiste uma periodicidade de teste.
   *
   * @param ativo           Se a periodicidade está ativa
   * @param dataInicio      Data de início do intervalo base
   * @param dataFim         Data de fim do intervalo base (pode ser null)
   * @param frequency       Frequência da recorrência (pode ser null)
   * @return Periodicidade persistida
   */
  protected Periodicidade criarPeriodicidade(Boolean ativo,
      java.time.LocalDate dataInicio, java.time.LocalDate dataFim,
      com.ia.core.quartz.model.periodicidade.Frequencia frequency) {

    Periodicidade periodicidade = Periodicidade.builder()
        .ativo(ativo)
        .build();

    // Configura intervalo base
    periodicidade.getIntervaloBase().setStartDate(dataInicio);
    periodicidade.getIntervaloBase().setEndDate(dataFim);

    // Configura recorrência se informada
    if (frequency != null) {
      Recorrencia recorrencia = new Recorrencia();
      recorrencia.setFrequency(frequency);
      periodicidade.setRegra(recorrencia);
    }

    entityManager.persist(periodicidade);
    entityManager.flush();
    entityManager.clear();

    return periodicidade;
  }

  /**
   * Cria uma periodicidade ativa com frequência DIARIAMENTE.
   *
   * @param dataInicio Data de início
   * @return Periodicidade criada
   */
  protected Periodicidade criarPeriodicidadeAtiva(java.time.LocalDate dataInicio) {
    return criarPeriodicidade(Boolean.TRUE, dataInicio, null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
  }

  /**
   * Cria uma periodicidade inativa.
   *
   * @param dataInicio Data de início
   * @return Periodicidade criada
   */
  protected Periodicidade criarPeriodicidadeInativa(java.time.LocalDate dataInicio) {
    return criarPeriodicidade(Boolean.FALSE, dataInicio, null,
        com.ia.core.quartz.model.periodicidade.Frequencia.DIARIAMENTE);
  }

  /**
   * Cria uma periodicidade sem regra de recorrência.
   *
   * @param ativo      Se está ativa
   * @param dataInicio Data de início
   * @return Periodicidade criada
   */
  protected Periodicidade criarPeriodicidadeSemRegra(Boolean ativo,
      java.time.LocalDate dataInicio) {
    return criarPeriodicidade(ativo, dataInicio, null, null);
  }

  /**
   * Limpa o contexto de persistência.
   */
  protected void limparContexto() {
    entityManager.flush();
    entityManager.clear();
  }
}
