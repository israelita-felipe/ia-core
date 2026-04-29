package com.ia.core.flyway.model;

import com.ia.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
/**
 * Classe que representa a entidade de domínio flyway execution.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a FlywayExecution
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FlywayExecution
  extends BaseEntity {
  /**
   * Mapeia para a coluna installed_rank. O valor é fornecido pelo banco do
   * Flyway, não é gerado automaticamente.
   */
  @Id
  @Column(name = "installed_rank")
  private Long id;

  @Column(name = "version", insertable = false, updatable = false)
  private Long version;

  @Column(name = "version", length = 50, insertable = false,
          updatable = false)
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

}
