package com.ia.test.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
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
 * Base class for integration tests of Quartz Repository layer. Uses H2
 * in-memory database for fast testing. Features: - Automatic transaction
 * rollback after each test - EntityManager injection for manual operations -
 * Test profile activation Usage:
 *
 * <pre>
 * {@code @ActiveProfiles("test")}
 * class MyQuartzRepositoryIT extends AbstractRepositoryIT {
 *     // Your test methods
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
public abstract class AbstractRepositoryIT {

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
