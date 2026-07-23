package com.ia.core.communication.service.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Configuração central do módulo Communication.
 * <p>
 * Responsável por injetar e expor o {@link CommunicationConfigurationProvider}
 * como fonte única de configuração do módulo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class CoreCommunicationConfig {

    @Getter
    private final CommunicationConfigurationProvider communicationConfigurationProvider;

}
