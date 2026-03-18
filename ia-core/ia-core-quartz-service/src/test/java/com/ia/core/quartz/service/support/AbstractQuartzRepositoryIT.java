package com.ia.core.quartz.service.support;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.test.integration.AbstractRepositoryIT;

/**
 * Base class for integration tests of Quartz Repository layer. Uses H2
 * in-memory database for fast testing. Features: - Automatic transaction
 * rollback after each test - EntityManager injection for manual operations -
 * Test profile activation Usage:
 *
 * <pre>
 * {@code @ActiveProfiles("test")}
 * class MyQuartzRepositoryIT extends AbstractQuartzRepositoryIT {
 *     // Your test methods
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@ActiveProfiles("test")
public abstract class AbstractQuartzRepositoryIT
  extends AbstractRepositoryIT {

  /**
   * Configuration for test context.
   */
  @Configuration
  @EnableJpaRepositories(basePackages = "com.ia.core.quartz.service")
  static class TestConfig
    extends AbstractRepositoryIT.AbstractTestConfig {

    @Override
    @Bean
    public DataSource dataSource() {
      return super.dataSource();
    }

    @Bean
    public DataSource quartzDataSource(DataSource dataSource) {
      try {
        java.sql.Connection conn = dataSource.getConnection();
        conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS QUARTZ")
            .execute();
        conn.close();
      } catch (Exception e) {
        // Schema may already exist
      }
      return dataSource;
    }

    @Override
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean em = super.entityManagerFactory(dataSource);
      em.setPackagesToScan("com.ia.core.quartz.model");
      return em;
    }

    @Override
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
      return super.transactionManager(entityManagerFactory);
    }
  }

}
