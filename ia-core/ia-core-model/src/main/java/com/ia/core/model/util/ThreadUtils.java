package com.ia.core.model.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Utilitários para manipulação de threads.
 * <p>
 * Fornece métodos auxiliares para operações relacionadas a threads.
 *
 * @author Israel Araújo
 */
@Slf4j
public class ThreadUtils {
  /** Loga o nome da thread atual */
  public static void logThreadName() {
    log.info("{}", Thread.currentThread().getName());
  }
}
