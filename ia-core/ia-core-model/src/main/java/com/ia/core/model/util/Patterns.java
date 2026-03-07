package com.ia.core.model.util;

/**
 * Classe utilitária que contém padrões regex para validação.
 *
 * <p>Fornece padrões pré-compilados para validação de dados comuns
 * como telefones e e-mails.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class Patterns {
  /**
   * Padrão regex para telefone celular brasileiro.
   *
   * <p>Aceita formatos: (XX) 9XXXX-XXXX, (XX)9XXXXXXXX, XX 9XXXX-XXXX, etc.
   */
  public static String TELEFONE_CELULAR = "^\\(?\\d{2}\\)?[\\s-]?[\\s9]?\\d{4}-?\\d{4}$";

  /**
   * Padrão regex para telefone fixo brasileiro.
   *
   * <p>Aceita formatos: (XX) XXXX-XXXX, XX XXXX-XXXX, etc.
   */
  public static String TELEFONE_FIXO = "^\\(?\\d{2}\\)?[\\s-]?\\d{4}-?\\d{4}$";

  /**
   * Padrão regex para e-mail.
   *
   * <p>Valida formato básico de e-mail: usuario@dominio
   */
  public static String EMAIL = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
}
