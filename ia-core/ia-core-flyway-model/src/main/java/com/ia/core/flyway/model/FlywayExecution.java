package com.ia.core.flyway.model;

import java.time.LocalDateTime;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.HasVersion;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entidade que mapeia a tabela de histórico de execuções do Flyway.
 * <p>
 * Esta entidade representa a tabela {@code flyway_schema_history} que é
 * automaticamente criada e mantida pelo Flyway. Os dados desta tabela são
 * somente leitura e representam o histórico de todas as migrações aplicadas ao
 * banco de dados.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@MappedSuperclass
public abstract class FlywayExecution
  extends BaseEntity {

  /**
   * Mapeia para a coluna installed_rank. O valor é fornecido pelo banco do
   * Flyway, não é gerado automaticamente.
   */
  @Column(name = "installed_rank")
  @Id
  private Long id;

  @Default
  @Version
  @Column(name = "version", columnDefinition = "bigint default 1",
          nullable = false)
  private Long version = HasVersion.DEFAULT_VERSION;

  @Column(name = "version", length = 50)
  private String migrationVersion;

  @Column(name = "description", length = 200)
  private String description;

  @Column(name = "type", length = 20)
  private String type;

  @Column(name = "script", length = 1000)
  private String script;

  @Column(name = "checksum")
  private Integer checksum;

  @Column(name = "installed_by", length = 100)
  private String installedBy;

  @Column(name = "installed_on")
  private LocalDateTime installedOn;

  @Column(name = "execution_time")
  private Integer executionTime;

  @Column(name = "success")
  private Boolean success;

  /**
   * Construtor padrão.
   */
  public FlywayExecution() {
    super();
  }

}
