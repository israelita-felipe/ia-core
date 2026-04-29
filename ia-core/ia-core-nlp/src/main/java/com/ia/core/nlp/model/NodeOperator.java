package com.ia.core.nlp.model;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Operador para manipulação de nós NLP.
 *
 * <p>Esta classe utilitária fornece operações estáticas para manipulação
 * de {@link Node}, incluindo cálculo de cosseno de similaridade e
 * operações recursivas em arrays de nós.
 *
 * <p><b>Operações suportadas:</b></p>
 * <ul>
 *   <li>Cálculo de cosseno de similaridade entre nós ({@link #coss(Node, Node)})</li>
 *   <li>Operação recursiva em arrays de nós ({@link #operate(Node[])})</li>
 * </ul>
 *
 * <p><b>Por quê usar NodeOperator?</b></p>
 * <ul>
 *   <li>Centraliza operações complexas de manipulação de nós</li>
 *   <li>Fornece cálculo de similaridade para análise de texto</li>
 *   <li>Implementa algoritmo recursivo para processamento de sequências</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see Node
 * @see CompactNode
 * @since 1.0.0
 */
public final class NodeOperator {

  /** Identidade para operações de multiplicação matricial. */
  public static final Node IDENTITY = Node
      .valueNotCompactOf(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO,
                         BigDecimal.ONE);

  /**
   * Construtor privado para prevenir instanciação.
   */
  private NodeOperator() {
    throw new UnsupportedOperationException("Classe utilitária - não instanciar");
  }

  /**
   * Calcula o cosseno de similaridade entre dois nós.
   *
   * <p>O cosseno é calculado usando o produto escalar dos vetores normalizados:
   * <pre>
   * cos(θ) = a₁·a₂ + c₁·c₂ + t₁·t₂ + g₁·g₂
   * </pre>
   *
   * @param nodeOne primeiro nó (não pode ser null)
   * @param nodeTwo segundo nó (não pode ser null)
   * @return valor do cosseno de similaridade entre os nós
   */
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
   * Aplica negação alternada nos elementos de um array de nós.
   *
   * <p>Elementos em índices pares (0, 2, 4...) são negados.
   *
   * @param newArray array de nós a ser modificado
   * @return o mesmo array com elementos negados alternadamente
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
   * Opera dois nós negados com um nó de multiplicação.
   *
   * @param negatedNode nó negado
   * @param positiveNode nó positivo
   * @param multiplyNode nó de multiplicação
   * @return resultado da operação
   */
  private static Node operate(Node negatedNode, Node positiveNode,
                              Node multiplyNode) {
    Node resultNegated = negatedNode.multiply(multiplyNode);
    Node resultPositive = positiveNode.multiply(multiplyNode);
    return resultNegated.add(resultPositive);
  }

  /**
   * Realiza operação recursiva em um array de nós.
   *
   * <p>Este método implementa um algoritmo recursivo que:
   * <ol>
   *   <li>Verifica condição de parada (array com 1 ou 2 elementos)</li>
   *   <li>Aplica negação alternada se necessário</li>
   *   <li>Opera nos dois primeiros nós com o terceiro</li>
   *   <li>Recursivamente processa o array reduzido</li>
   * </ol>
   *
   * @param nodes array de nós para operar (não pode ser null ou vazio)
   * @return nó resultante da operação, normalizado
   * @throws IllegalArgumentException se o array for null ou vazio
   */
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
