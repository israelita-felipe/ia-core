package com.ia.core.nlp.model;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
@Getter
public class CompactNode {

  public static CompactNode valueOf(BigDecimal x, BigDecimal y) {
    CompactNode node = new CompactNode();
    node.x = x;
    node.y = y;
    return node;
  }
  private BigDecimal x = BigDecimal.ONE;

  private BigDecimal y = BigDecimal.ONE;

  private boolean negated = false;

  public CompactNode multiply(BigDecimal value) {
    CompactNode valueOf = CompactNode.valueOf(this.x.multiply(value),
                                              this.y.multiply(value));
    valueOf.negated = this.negated;
    return valueOf;
  }

  public CompactNode multiply(CompactNode node) {
    CompactNode valueOf = CompactNode.valueOf(this.x.multiply(node.x),
                                              this.y.multiply(node.y));
    valueOf.negated = this.negated;
    return valueOf;
  }

  public CompactNode negate() {
    CompactNode multiply = this.multiply(BigDecimal.ONE.negate());
    multiply.negated = true;
    return multiply;
  }

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
