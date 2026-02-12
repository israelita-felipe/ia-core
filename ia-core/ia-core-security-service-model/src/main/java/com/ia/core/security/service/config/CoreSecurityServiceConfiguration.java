package com.ia.core.security.service.config;

import org.springframework.context.annotation.Configuration;

/**
 * Classe abstrata de configuração base para serviços de segurança.
 * Fornece configuração comum compartilhada entre implementações de segurança.
 * <p>
 * Esta classe serve como ponto de entrada para a configuração de segurança
 * e pode ser estendida por classes filhas para adicionar configurações
 * específicas.
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0
 * @since 1.0
 */
@Configuration
public abstract class CoreSecurityServiceConfiguration {

  /**
   * Construtor padrão para configuração de segurança.
   * Inicializa os componentes necessários para o funcionamento
   * dos serviços de segurança.
   */
  protected CoreSecurityServiceConfiguration() {
    // Inicialização de componentes comuns de segurança
  }
}
