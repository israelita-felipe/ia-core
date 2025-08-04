package com.ia.core.nlp.model;

import java.math.BigDecimal;

/**
 * @author Israel Araújo
 */
public class NodeCreator {

  public static Node[] fromChar(Character value) {
    String binaryString = String
        .format("%8s", Integer.toBinaryString(value)).replaceAll(" ", "0");
    Node[] values = new Node[4];
    for (int i = 0; i < 4; i++) {
      int inicio = i * 2;
      int fim = Math.min(inicio + 2, binaryString.length());
      EIIPEnum eiip = toEIIP(binaryString.substring(inicio, fim));
      switch (eiip) {
      case G: {
        values[i] = Node.valueOf(eiip.getValue(), BigDecimal.ONE,
                                 BigDecimal.ONE, BigDecimal.ONE);
        break;
      }
      case A: {
        values[i] = Node.valueOf(BigDecimal.ONE, eiip.getValue(),
                                 BigDecimal.ONE, BigDecimal.ONE);
        break;
      }
      case T: {
        values[i] = Node.valueOf(BigDecimal.ONE, BigDecimal.ONE,
                                 eiip.getValue(), BigDecimal.ONE);
        break;
      }
      case C: {
        values[i] = Node.valueOf(BigDecimal.ONE, BigDecimal.ONE,
                                 BigDecimal.ONE, eiip.getValue());
        break;
      }
      default:
        values[i] = null;
        break;
      }
    }
    return values;
  }

  public static Node[] fromText(String text) {
    String[] splitted = text.split("\\s+");
    Node[] nodes = new Node[splitted.length];
    for (int i = 0; i < splitted.length; i++) {
      Node[] fromWord = fromWord(splitted[i]);
      nodes[i] = NodeOperator.operate(fromWord);
    }
    return nodes;
  }

  public static Node[] fromText(String[] text) {
    Node[] nodes = new Node[text.length];
    for (int i = 0; i < text.length; i++) {
      Node[] fromWord = fromWord(text[i]);
      nodes[i] = NodeOperator.operate(fromWord);
    }
    return nodes;
  }

  public static Node[] fromWord(String value) {
    if (value.matches("(.*)?\\s+(.*)?")) {
      throw new RuntimeException("A palavra não pode conter espaços");
    }
    Node[] vetor = new Node[value.length()];
    int index = 0;
    for (char charactere : value.toCharArray()) {
      vetor[index] = NodeOperator.operate(fromChar(charactere));
      index++;
    }
    return vetor;
  }

  public static void main(String[] args) {
    System.out.println(NodeOperator.operate(NodeCreator
        .fromText("teste de estouro de ponto flutuante")));
    System.out.println(NodeOperator
        .operate(NodeCreator.fromText("floating point overflow test")));

  }

  /**
   * Converte uma string binária em nós
   *
   * @param value representação binária de dois dígitos de um determinado
   *              caractere
   * @return {@link Node}
   */
  private static EIIPEnum toEIIP(String value) {
    switch (value) {
    case "00": {
      return EIIPEnum.G;
    }
    case "01": {
      return EIIPEnum.A;
    }
    case "10": {
      return EIIPEnum.T;
    }
    case "11": {
      return EIIPEnum.C;
    }
    default: {
      return null;
    }
    }
  }
}
