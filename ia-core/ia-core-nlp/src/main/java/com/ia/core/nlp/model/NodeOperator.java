package com.ia.core.nlp.model;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author Israel Araújo
 */
public class NodeOperator {

  /** identidade */
  public static final Node IDENTITY = Node
      .valueNotCompactOf(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO,
                         BigDecimal.ONE);

  public static BigDecimal coss(Node nodeOne, Node nodeTwo) {
    // sempre pegar vetores unitários
    Node nodeOneNF = nodeOne.normalize();
    Node nodeTwoNF = nodeTwo.normalize();
    BigDecimal a = nodeOneNF.getA().multiply(nodeTwoNF.getA());
    BigDecimal c = nodeOneNF.getC().multiply(nodeTwoNF.getC());
    BigDecimal t = nodeOneNF.getT().multiply(nodeTwoNF.getT());
    BigDecimal g = nodeOneNF.getG().multiply(nodeTwoNF.getG());
    return a.add(c).add(t).add(g);
  }

  /**
   * @param newArray
   * @return
   */
  private static Node[] oddNegation(Node[] newArray) {
    for (int i = 0; i < newArray.length; i++) {
      if (i % 2 == 0) {
        newArray[i] = newArray[i].negate();
      }
    }
    return newArray;
  }

  /**
   * @param negatedNode
   * @param positiveNode
   * @param multiplyNode
   * @return
   */
  private static Node operate(Node negatedNode, Node positiveNode,
                              Node multiplyNode) {
    Node resultNegated = negatedNode.multiply(multiplyNode);
    Node resultPositive = positiveNode.multiply(multiplyNode);
    return resultNegated.add(resultPositive);
  }

  public static Node operate(Node[] nodes) {
    // condição de parada
    if (nodes.length == 2 && nodes[1].equals(IDENTITY)
        || nodes.length == 1) {
      return nodes[0].normalize();
    }
    // primeira interação
    if (!nodes[nodes.length - 1].equals(IDENTITY)) {
      nodes = oddNegation(nodes);
      Node[] newArray = Arrays.copyOf(nodes, nodes.length + 1);
      newArray[newArray.length - 1] = IDENTITY;
      return operate(newArray);
    }
    // realiza a operação nos dois primeiros nós
    Node negatedNode = nodes[0];
    Node positiveNode = nodes[1];
    Node multiplyNode = nodes[2];
    Node[] newArray = Arrays.copyOfRange(nodes, 2, nodes.length);
    newArray[0] = operate(negatedNode, positiveNode, multiplyNode);
    return operate(newArray);
  }
}
