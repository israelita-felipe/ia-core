package com.ia.core.model.util;

/**
 * Classe utilitária para padrões.
 *
 * @author Israel Araújo
 */
public class Patterns {
  /** Padrão para telefone celular */
  public static String TELEFONE_CELULAR = "^\\(?\\d{2}\\)?[\\s-]?[\\s9]?\\d{4}-?\\d{4}$";
  /** Padrão para telefone fixo */
  public static String TELEFONE_FIXO = "^\\(?\\d{2}\\)?[\\s-]?\\d{4}-?\\d{4}$";
  /** Padrão para e-mail */
  public static String EMAIL = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
}
