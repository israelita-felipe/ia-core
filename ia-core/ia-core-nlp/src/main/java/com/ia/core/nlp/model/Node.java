package com.ia.core.nlp.model;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
@Getter
public class Node {

  public static Node valueNotCompactOf(BigDecimal g, BigDecimal a,
                                       BigDecimal t, BigDecimal c) {
    Node node = new Node();
    node.g = g;
    node.a = a;
    node.t = t;
    node.c = c;
    return node;
  }
  public static Node valueOf(BigDecimal g, BigDecimal a, BigDecimal t,
                             BigDecimal c) {
    Node node = new Node();
    node.g = g;
    node.a = a;
    node.t = t;
    node.c = c;
    // retorna o nó em sua forma compacta para já embarcar semântica/sintaxe
    return node.compact();
  }
  private BigDecimal g = BigDecimal.ONE;
  private BigDecimal a = BigDecimal.ONE;
  private BigDecimal t = BigDecimal.ONE;

  private BigDecimal c = BigDecimal.ONE;

  private boolean negated = false;

  /**
   * @param resultPositive
   * @return
   */
  public Node add(Node node) {
    Node valueNotCompactOf = Node
        .valueNotCompactOf(this.g.add(node.g), this.a.add(node.a),
                           this.t.add(node.t), this.c.add(node.c));
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;
  }

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

  public Node multiply(BigDecimal value) {
    Node valueNotCompactOf = Node
        .valueNotCompactOf(this.g.multiply(value), this.a.multiply(value),
                           this.t.multiply(value), this.c.multiply(value));
    valueNotCompactOf.negated = this.negated;
    return valueNotCompactOf;
  }

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

  public Node negate() {
    Node multiply = multiply(BigDecimal.ONE.negate());
    multiply.negated = true;
    return multiply;
  }

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
