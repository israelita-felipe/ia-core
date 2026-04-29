package com.ia.core.nlp.model;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Factory para criação de nós NLP a partir de texto e caracteres.
 *
 * <p>Esta classe utilitária fornece métodos estáticos para converter
 * texto, palavras e caracteres em arrays de {@link Node} para processamento
 * NLP usando o algoritmo EIIP (Electron-Ion Interaction Pseudopotential).
 *
 * <p><b>Operações suportadas:</b></p>
 * <ul>
 *   <li>Conversão de caractere para nós binários ({@link #fromChar(Character)})</li>
 *   <li>Conversão de texto para nós ({@link #fromText(String)})</li>
 *   <li>Conversão de palavra para nós ({@link #fromWord(String)})</li>
 * </ul>
 *
 * <p><b>Por quê usar NodeCreator?</b></p>
 * <ul>
 *   <li>Centraliza a lógica de conversão de texto para representação numérica</li>
 *   <li>Implementa algoritmo EIIP para mapeamento de sequências</li>
 *   <li>Integra com {@link NodeOperator} para processamento de resultados</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see Node
 * @see NodeOperator
 * @see EIIPEnum
 * @since 1.0.0
 */
@Slf4j
public final class NodeCreator {

  /**
   * Construtor privado para prevenir instanciação.
   */
  private NodeCreator() {
    throw new UnsupportedOperationException("Classe utilitária - não instanciar");
  }

  /**
   * Converte um caractere em um array de 4 nós baseado na representação binária.
   *
   * <p>Cada caractere é convertido para sua representação binária de 8 bits,
   * e cada par de bits é mapeado para um valor EIIPEnum (G, A, T, C).
   *
   * @param value caractere a ser convertido
   * @return array de 4 {@link Node} representando o caractere
   */
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

  /**
   * Converte um texto em array de nós, separando por espaços.
   *
   * @param text texto a ser convertido
   * @return array de {@link Node} processados
   */
  public static Node[] fromText(String text) {
    String[] splitted = text.split("\\s+");
    Node[] nodes = new Node[splitted.length];
    for (int i = 0; i < splitted.length; i++) {
      Node[] fromWord = fromWord(splitted[i]);
      nodes[i] = NodeOperator.operate(fromWord);
    }
    return nodes;
  }

  /**
   * Converte um array de strings em array de nós.
   *
   * @param text array de strings a serem convertidas
   * @return array de {@link Node} processados
   */
  public static Node[] fromText(String[] text) {
    Node[] nodes = new Node[text.length];
    for (int i = 0; i < text.length; i++) {
      Node[] fromWord = fromWord(text[i]);
      nodes[i] = NodeOperator.operate(fromWord);
    }
    return nodes;
  }

  /**
   * Converte uma palavra em array de nós.
   *
   * @param value palavra a ser convertida (não pode conter espaços)
   * @return array de {@link Node} representando a palavra
   * @throws IllegalArgumentException se a palavra contiver espaços
   */
  public static Node[] fromWord(String value) {
    if (value.matches("(.*)?\\s+(.*)?")) {
      throw new IllegalArgumentException("A palavra não pode conter espaços");
    }
    Node[] vetor = new Node[value.length()];
    int index = 0;
    for (char caractere : value.toCharArray()) {
      vetor[index] = NodeOperator.operate(fromChar(caractere));
      index++;
    }
    return vetor;
  }

  /**
   * Método principal para teste de conversão de texto.
   *
   * @param args argumentos de linha de comando (não usados)
   */
  public static void main(String[] args) {
    log.info("Teste de estouro de ponto flutuante (PT):");
    log.info("{}", NodeOperator.operate(NodeCreator
        .fromText("teste de estouro de ponto flutuante")));
    log.info("Floating point overflow test (EN):");
    log.info("{}", NodeOperator
        .operate(NodeCreator.fromText("floating point overflow test")));
  }

  /**
   * Converte uma string binária de dois dígitos em um valor EIIPEnum.
   *
   * @param value representação binária ("00", "01", "10", "11")
   * @return {@link EIIPEnum} correspondente ou null se inválido
   */
  private static EIIPEnum toEIIP(String value) {
    return switch (value) {
      case "00" -> EIIPEnum.G;
      case "01" -> EIIPEnum.A;
      case "10" -> EIIPEnum.T;
      case "11" -> EIIPEnum.C;
      default -> null;
    };
  }
}
