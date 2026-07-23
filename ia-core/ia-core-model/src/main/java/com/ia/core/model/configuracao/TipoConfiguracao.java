package com.ia.core.model.configuracao;

/**
 * Tipo de configuração do sistema.
 * <p>
 * Define os tipos de dados suportados para armazenamento de configurações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum TipoConfiguracao {

    /**
     * String simples.
     */
    STRING,

    /**
     * Número inteiro.
     */
    INTEGER,

    /**
     * Valor booleano.
     */
    BOOLEAN,

    /**
     * JSON estruturado.
     */
    JSON,

    /**
     * String criptografada.
     */
    ENCRYPTED_STRING
}