package com.ia.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe base abstrata para todas as entidades do sistema. Fornece a estrutura
 * comum para persistência no banco de dados, incluindo identificação única,
 * controle de versão para lock otimista e ordenação natural.
 * <p>
 * Esta classe define o padrão de identidade para todas as entidades do domínio,
 * utilizando {@link Long} como tipo de identificador gerado por TSID
 * (Timestamp-Sortable ID) para garantir ordenação temporal e distribuição.
 * <p>
 * <b>Por quê usar BaseEntity?</b>
 * </p>
 * <ul>
 * <li>Centraliza a lógica de identidade comum a todas as entidades</li>
 * <li>Proporciona ordenação natural baseada no ID para Collections</li>
 * <li>Implementa lock otimista para controle de concorrência</li>
 * <li>Reduz código duplicado nas classes de entidade</li>
 * </ul>
 * <p>
 * <b>Exemplo de uso:</b>
 * </p>
 * {@code @Entity @Table(name = "pessoas") public class Pessoa extends
 * BaseEntity { private String nome; private String email; } }
 *
 * @see HasVersion
 * @see Serializable
 * @see Comparable
 * @since 1.0.0
 * @author Israel Araújo
 */
@SuppressWarnings("serial")
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity
  implements Serializable, HasVersion<Long>, Comparable<BaseEntity> {

  /**
   * Identificador único da entidade no banco de dados.
   * <p>
   * Gerado automaticamente via TSID (Timestamp-Sortable ID) com algoritmo
   * TSID-4096 para garantir ordenação temporal e distribuição uniforme.
   *
   * @return o identificador único da entidade, nunca {@code null} após
   *         persistência
   */
  @Id
  private Long id;

  /**
   * Versão para controle de lock otimista.
   * <p>
   * Incrementado automaticamente pelo JPA a cada atualização da entidade. Usado
   * para evitar conflitos de concorrência em ambientes com múltiplas transações
   * simultâneas.
   *
   * @return a versão atual da entidade, nunca {@code null}
   */
  @Default
  @Version
  @Column(name = "version", columnDefinition = "bigint default 1",
          nullable = false)
  private Long version = HasVersion.DEFAULT_VERSION;

  /**
   * Callback executado antes da persistência da entidade.
   * <p>
   * Gera automaticamente o identificador único (TSID) caso não tenha sido
   * fornecido. Este método é chamado pelo JPA automaticamente durante a
   * operação de persistência.
   *
   * @throws IllegalStateException se o gerador TSID não estiver disponível
   */
  @PrePersist
  public void onCreate() {
    if (this.id == null) {
      this.id = TSID.Factory.getTsid4096().toLong();
    }
  }

  /**
   * Compara esta entidade com outra para ordenação natural.
   * <p>
   * A comparação é baseada no identificador {@link #id}, seguindo a lógica:
   * <ul>
   * <li>Entidades com {@code id} não-nulo são maiores que entidades com
   * {@code id} nulo</li>
   * <li>Duas entidades com {@code id} nulo são iguais</li>
   * <li>Duas entidades com {@code id} não-nulo são comparadas
   * numericamente</li>
   * </ul>
   * <p>
   * <b>Exemplo de uso:</b>
   * </p>
   * {@code
   * List<Pessoa> pessoas = new ArrayList<>();
   * Collections.sort(pessoas); // Ordena pela naturais das entidades
   * }
   *
   * @param o a entidade a ser comparada, pode ser {@code null}
   * @return valor negativo se esta entidade menor, zero se igual, positivo se
   *         maior
   * @throws ClassCastException se o objeto passado não é uma instância de
   *                            {@link BaseEntity}
   */
  @Override
  public int compareTo(BaseEntity o) {
    if (this.id == null) {
      if (o.id == null) {
        return 0;
      }
      return -1;
    }
    if (o.id == null) {
      return 1;
    }
    return this.id.compareTo(o.id);
  }

  /**
   * Verifica se esta entidade é igual a outro objeto.
   * <p>
   * Duas entidades são consideradas iguais se:
   * <ul>
   * <li>São a mesma instância em memória ({@code this == obj})</li>
   * <li>Ambas possuem {@code id} não-nulo e os valores são iguais</li>
   * <li>O objeto é uma instância de {@link BaseEntity} e possui o mesmo
   * {@code id}</li>
   * </ul>
   * <p>
   * <b>Nota:</b> Este método compara apenas o identificador, não o conteúdo dos
   * demais campos da entidade. Isso segue o padrão de identidade do domínio.
   *
   * @param obj o objeto a ser comparado, pode ser {@code null}
   * @return {@code true} se as entidades são iguais, {@code false} caso
   *         contrário
   * @see #hashCode()
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (id == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    BaseEntity other = (BaseEntity) obj;
    return Objects.equals(id, other.id);
  }

  /**
   * Gera o código de hash para esta entidade.
   * <p>
   * O cálculo do hash é baseado exclusivamente no identificador {@link #id},
   * seguindo o mesmo princípio do método {@link #equals(Object)}.
   * <p>
   * <b>Contrato com {@code equals}:</b> Se duas entidades são iguais segundo
   * {@link #equals(Object)}, este método deve retornar o mesmo valor de hash.
   *
   * @return o código de hash da entidade
   * @see #equals(Object)
   */
  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }
}
