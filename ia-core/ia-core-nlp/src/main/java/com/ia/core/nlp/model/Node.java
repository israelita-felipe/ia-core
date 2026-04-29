package com.ia.core.nlp.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Representa um nó numérico para processamento de linguagem natural (NLP).
 *
 * <p>Esta classe implementa uma estrutura matricial 2x2 representada pelos
 * valores G, A, T, C, que são usados para operações matemáticas no contexto
 * de processamento de sequências biológicas e análise de texto.
 *
 * <p><b>Operações suportadas:</b></p>
 * <ul>
 *   <li>Adição de nós ({@link #add(Node)})</li>
 *   <li>Multiplicação escalar e matricial ({@link #multiply(BigDecimal)}, {@link #multiply(Node)})</li>
 *   <li>Negação ({@link #negate()})</li>
 *   <li>Normalização ({@link #normalize()})</li>
 *   <li>Compactação ({@link #compact()})</li>
 * </ul>
 *
 * <p><b>Por quê usar Node?</b></p>
 * <ul>
 *   <li>Representa valores numéricos para análise de sequências</li>
 *   <li>Suporta operações matriciais para processamento NLP</li>
 *   <li>Integra com {@link NodeOperator} para operações complexas</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see NodeOperator
 * @see CompactNode
 * @see EIIPEnum
 * @since 1.0.0
 */
@Getter
public class Node {

  /** Valor G (Guanina) do nó. */
  private final BigDecimal g;

  /** Valor A (Adenina) do nó. */
  private final BigDecimal a;

  /** Valor T (Timina) do nó. */
  private final BigDecimal t;

  /** Valor C (Citosina) do nó. */
  private final BigDecimal c;

  /** Indica se o nó está negado. */
  boolean negated = false;

  /**
   * Construtor privado para controle de criação via factory method.
   *
   * @param g valor G
   * @param a valor A
   * @param t valor T
   * @param c valor C
   */
  private Node(BigDecimal g, BigDecimal a, BigDecimal t, BigDecimal c) {
    this.g = g;
    this.a = a;
    this.t = t;
    this.c = c;
  }

  /**
   * Cria um novo nó com os valores especificados.
   *
   * @param g valor G
   * @param a valor A
   * @param t valor T
   * @param c valor C
   * @return novo {@link Node} com os valores informados
   */
  public static Node valueOf(BigDecimal g, BigDecimal a, BigDecimal t, BigDecimal c) {
    return new Node(g, a, t, c);
  }

  /**
   * Cria um nó não compacto com os valores especificados.
   *
   * @param g valor G
   * @param a valor A
   * @param t valor T
   * @param c valor C
   * @return novo {@link Node} não compacto
   */
  public static Node valueNotCompactOf(BigDecimal g, BigDecimal a, BigDecimal t, BigDecimal c) {
    return new Node(g, a, t, c);
  }

  /**
   * Adiciona este nó com outro nó, retornando um novo nó com os valores somados.
   *
   * @param node nó a ser adicionado
   * @return novo {@link Node} com os valores somados
   */
  public Node add(Node node) {
    Node valueNotCompactOf = Node
        .valueNotCompactOf(this.g.add(node.g), this.a.add(node.a),
                           this.t.add(node.t), this.c.add(node.c));
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;
  }

  /**
   * Compacta este nó em uma representação mais simples usando {@link CompactNode}.
   *
   * <p>A compactação transforma a matriz 2x2 em uma representação 2D usando
   * operações lógicas: (a and not b) or c = (a->b)->c
   *
   * @return nó compactado
   */
  public Node compact() {
    // G A T C
    // 1-2
    CompactNode cnodeOne = CompactNode.valueOf(g, a);
    // 2-3
    CompactNode cnodeTwo = CompactNode.valueOf(a, t);
    // 3-4
    CompactNode cnodeThree = CompactNode.valueOf(t, c);
    // (a and not b) or c = (a->b)->c
    CompactNode cnodeResult = cnodeOne.multiply(cnodeTwo.negate());
    // matriz 4x4 resultante
    Node valueNotCompactOf = Node
        .valueNotCompactOf(cnodeResult.getX(), cnodeResult.getY(),
                           cnodeThree.getX(), cnodeThree.getY());
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;
  }

  /**
   * Multiplica todos os valores deste nó por um escalar.
   *
   * @param value valor escalar para multiplicação
   * @return novo {@link Node} com os valores multiplicados
   */
  public Node multiply(BigDecimal value) {
    Node valueNotCompactOf = Node
        .valueNotCompactOf(this.g.multiply(value), this.a.multiply(value),
                           this.t.multiply(value), this.c.multiply(value));
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;
  }

  /**
   * Multiplica este nó com outro nó usando multiplicação matricial 2x2.
   *
   * <p>A multiplicação segue a fórmula:
   * <pre>
   * |g a|   |g' a'|   |g*g' + a*t'   g*a' + a*c'|
   * |t c| x |t' c'| = |t*g' + c*t'   t*a' + c*c'|
   * </pre>
   *
   * @param node nó a ser multiplicado
   * @return novo {@link Node} resultante da multiplicação matricial
   */
  public Node multiply(Node node) {
    // multiplicação de matrizes
    Node valueNotCompactOf = Node
        .valueNotCompactOf(g.multiply(node.g).add(a.multiply(node.t)),
                           g.multiply(node.a).add(a.multiply(node.c)),
                           t.multiply(node.g).add(c.multiply(node.t)),
                           t.multiply(node.a).add(c.multiply(node.c)));
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;

  }

  /**
   * Nega este nó, multiplicando todos os valores por -1.
   *
   * @return novo {@link Node} com valores negados
   */
  public Node negate() {
    Node multiply = multiply(BigDecimal.ONE.negate());
    multiply.negated = true;
    return multiply;
  }

  /**
   * Normaliza este nó dividindo todos os valores pela norma euclidiana.
   *
   * <p>A norma é calculada como: sqrt(g² + a² + t² + c²)
   *
   * @return novo {@link Node} normalizado
   */
  public Node normalize() {
    BigDecimal soma = this.g.pow(2).add(this.a.pow(2)).add(this.t.pow(2))
        .add(this.c.pow(2)).sqrt(MathContext.DECIMAL128);
    Node valueOf = Node
        .valueNotCompactOf(this.g.divide(soma, MathContext.DECIMAL128),
                           this.a.divide(soma, MathContext.DECIMAL128),
                           this.t.divide(soma, MathContext.DECIMAL128),
                           this.c.divide(soma, MathContext.DECIMAL128));
    valueOf.negated = this.negated;
    return valueOf;
  }

  @Override
  public String toString() {
    return String.format("%s, %s, %s, %s", g, a, t, c);
  }

}
