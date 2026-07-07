package com.ia.test.service;

import com.ia.test.CoreBaseUnitTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Classe base para testes de integração da camada de Repository.
 * <p>
 * Utiliza banco de dados em memória H2 para testes rápidos.
 * Características:
 * - Rollback automático de transação após cada teste
 * - Injeção de EntityManager para operações manuais
 * - Ativação de perfil de teste
 *
 * <p>
 * Uso:
 *
 * <pre>
 * {@code @ActiveProfiles("test")}
 * class MyQuartzRepositoryIT extends CoreAbstractRepositoryIT {
 *     // Seus métodos de teste
 * }
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Disabled("Abstract base class - not a test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("com/ia/test")
public abstract class CoreAbstractRepositoryIT extends CoreBaseUnitTest {

  @PersistenceContext
  protected EntityManager entityManager;

  /**
   * Configuration for test context.
   */
  @Configuration
  public static abstract class AbstractTestConfig {

    public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .setType(EmbeddedDatabaseType.HSQL)
          .setName("testdb;DB_CLOSE_DELAY=-1;").build();
    }

    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);

      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setGenerateDdl(true);
      vendorAdapter.setShowSql(true);
      em.setJpaVendorAdapter(vendorAdapter);

      java.util.Map<String, String> props = new java.util.HashMap<>();
      props.put("hibernate.hbm2ddl.auto", "create-drop");
      props.put("hibernate.default_schema", "PUBLIC");
      em.setJpaPropertyMap(props);

      return em;
    }

    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager
          .setEntityManagerFactory(entityManagerFactory.getObject());
      return transactionManager;
    }
  }

  /**
   * Clear the entity manager before each test to ensure isolation.
   */
  @BeforeEach
  void setUp() {
    entityManager.clear();
  }

  /**
   * Flush and clear the persistence context. Useful to force synchronization
   * with database.
   */
  protected void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  /**
   * Persist and flush an entity.
   *
   * @param entity the entity to persist
   * @return the persisted entity
   */
  protected <T> T persistAndFlush(T entity) {
    entityManager.persist(entity);
    entityManager.flush();
    return entity;
  }
}
