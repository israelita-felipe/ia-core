package com.ia.core.llm.model.agente;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Agente especialista para orquestração multi-agente.
 * <p>
 * Representa um sub-agente especialista que pode ser orquestrado pelo spring-ai-agent-utils.
 * As configurações são armazenadas em banco de dados em vez de arquivos YAML, seguindo o padrão ia-core.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Entity
@Table(name = Agente.TABLE_NAME, schema = Agente.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Agente
  extends BaseEntity {

  private static final long serialVersionUID = 1234567890123456789L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AGENTE";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  /**
   * Identificador único do agente (ex: llm.core, pessoa.especialista).
   * Usado pelo IaCoreSubagentResolver para resolver sub-agentes.
   */
  @Column(name = "identificador", unique = true, nullable = false, length = 100)
  private String identificador;

  /**
   * Nome apresentável do agente na UI.
   */
  @Column(name = "titulo", nullable = false, length = 200)
  private String titulo;

  /**
   * Descrição do propósito do agente.
   */
  @Column(name = "descricao", length = 1000)
  private String descricao;

  /**
   * Instruções do sistema (equivalente ao YAML frontmatter do spring-ai-agent-utils).
   * Contém as instruções que o agente deve seguir ao processar solicitações.
   */
  @Lob
  @Column(name = "instrucoes")
  private String instrucoes;

  /**
   * Modelo LLM preferido para este agente (ex: sonnet, opus, haiku, llama3.2-vision).
   * Usado para multi-model routing.
   */
  @Column(name = "modelo", length = 100)
  private String modelo;

  /**
   * Lista de ferramentas autorizadas para este agente.
   * O orquestrador restringe invocações apenas às ferramentas desta lista.
   * Inclui tanto ferramentas atômicas quanto skills (tipo=SKILL).
   */
  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = Agente.FERRAMENTA_JOIN_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "agente_id"),
      inverseJoinColumns = @JoinColumn(name = "ferramenta_id"))
  private List<Ferramenta> ferramentas = new ArrayList<>();

  /**
   * Indica se o agente está disponível para orquestração.
   */
  @Default
  @Column(name = "ativo", nullable = false, length = 1)
  private boolean ativo = true;

  /**
   * Módulo ou pacote fonte (ex: ia-core-pessoa-service).
   * Usado para identificar a origem do agente especialista.
   */
  @Column(name = "modulo_origem", length = 200)
  private String moduloOrigem;

  /**
   * Metadados genéricos armazenados como mapa chave-valor.
   * Permite armazenar especificidades de diferentes tipos de agentes
   * sem criar campos específicos na tabela.
   */
  @ElementCollection
  @CollectionTable(
      name = "AGENTE_METADADOS",
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "agente_id"))
  @MapKeyColumn(name = "chave")
  @Column(name = "valor")
  @Default
  private Map<String, String> metadados = new HashMap<>();

  public static final String FERRAMENTA_JOIN_TABLE = LLMModel.TABLE_PREFIX + "AGENTE_FERRAMENTA";

  /**
   * Adiciona uma ferramenta à lista de ferramentas autorizadas.
   *
   * @param ferramenta ferramenta a ser adicionada
   */
  public void adicionarFerramenta(Ferramenta ferramenta) {
    log.debug("Adicionando ferramenta {} ao agente {}", ferramenta.getIdentificador(), this.identificador);
    if (!this.ferramentas.contains(ferramenta)) {
      this.ferramentas.add(ferramenta);
    }
  }

  /**
   * Remove uma ferramenta da lista de ferramentas autorizadas.
   *
   * @param ferramenta ferramenta a ser removida
   */
  public void removerFerramenta(Ferramenta ferramenta) {
    log.debug("Removendo ferramenta {} do agente {}", ferramenta.getIdentificador(), this.identificador);
    this.ferramentas.remove(ferramenta);
  }
}
