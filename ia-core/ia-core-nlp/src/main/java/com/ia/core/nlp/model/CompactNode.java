package com.ia.core.nlp.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Representa um nó compacto para processamento NLP.
 *
 * <p>Esta classe implementa uma representação simplificada de um {@link Node}
 * usando apenas dois valores (x, y), utilizada para operações de compactação
 * e transformação de matrizes 2x2 em vetores 2D.
 *
 * <p><b>Por quê usar CompactNode?</b></p>
 * <ul>
 *   <li>Reduz complexidade de operações matriciais</li>
 *   <li>Permite compactação eficiente de dados NLP</li>
 *   <li>Integra com {@link Node#compact()} para transformação</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see Node
 * @see NodeOperator
 * @since 1.0.0
 */
@Getter
public class CompactNode {

  /** Valor X do nó compacto. */
  private BigDecimal x;

  /** Valor Y do nó compacto. */
  private BigDecimal y;

  /** Indica se o nó está negado. */
  private boolean negated;

  /**
   * Cria um novo nó compacto com os valores especificados.
   *
   * @param x valor X
   * @param y valor Y
   * @return novo {@link CompactNode} com os valores informados
   */
  public static CompactNode valueOf(BigDecimal x, BigDecimal y) {
    CompactNode node = new CompactNode();
    node.x = x;
    node.y = y;
    node.negated = false;
    return node;
  }

  /**
   * Construtor padrão privado.
   */
  private CompactNode() {
    this.x = BigDecimal.ONE;
    this.y = BigDecimal.ONE;
    this.negated = false;
  }

  /**
   * Multiplica todos os valores deste nó compacto por um escalar.
   *
   * @param value valor escalar para multiplicação
   * @return novo {@link CompactNode} com os valores multiplicados
   */
  public CompactNode multiply(BigDecimal value) {
    CompactNode valueOf = CompactNode.valueOf(this.x.multiply(value),
                                              this.y.multiply(value));
    valueOf.negated = this.negated;
    return valueOf;
  }

  /**
   * Multiplica este nó compacto com outro nó compacto.
   *
   * @param node nó compacto a ser multiplicado
   * @return novo {@link CompactNode} resultante da multiplicação
   */
  public CompactNode multiply(CompactNode node) {
    CompactNode valueOf = CompactNode.valueOf(this.x.multiply(node.x),
                                              this.y.multiply(node.y));
    valueOf.negated = this.negated;
    return valueOf;
  }

  /**
   * Nega este nó compacto, multiplicando todos os valores por -1.
   *
   * @return novo {@link CompactNode} com valores negados
   */
  public CompactNode negate() {
    CompactNode multiply = this.multiply(BigDecimal.ONE.negate());
    multiply.negated = true;
    return multiply;
  }

  /**
   * Normaliza este nó compacto dividindo todos os valores pela norma euclidiana.
   *
   * <p>A norma é calculada como: sqrt(x² + y²)
   *
   * @return novo {@link CompactNode} normalizado
   */
  public CompactNode normalize() {
    BigDecimal soma = this.x.pow(2).add(this.y.pow(2))
        .sqrt(MathContext.DECIMAL128);
    CompactNode valueOf = CompactNode
        .valueOf(this.x.divide(soma, MathContext.DECIMAL128),
                 this.y.divide(soma, MathContext.DECIMAL128));
    valueOf.negated = this.negated;
    return valueOf;
  }

  @Override
  public String toString() {
    return String.format("%s, %s", x, y);
  }

}
