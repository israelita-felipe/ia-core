package com.ia.core.model.util;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class ThreadUtils {
  /** Loga o nome da thread atual */
  public static void logThreadName() {
    log.info("{}", Thread.currentThread().getName());
  }
}
