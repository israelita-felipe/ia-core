package com.ia.core.security.service.model.user;

import com.ia.core.security.service.utils.CryptUtils;

/**
 * Interface para encoder de senha.
 * <p>
 * Define o contrato para codificação e verificação de senhas.
 * Métodos de criptografia/descriptografia foram movidos para {@link CryptUtils}.
 *
 * @author Israel Araújo
 */
public interface UserPasswordEncoder {

    /**
     * Codifica uma senha em texto plano.
     * <p>
     * BCrypt gera salt automaticamente internamente.
     *
     * @param rawPassword senha a ser codificada
     * @return senha codificada
     */
    String encode(CharSequence rawPassword);

    /**
     * Verifica se uma senha corresponde a senha codificada armazenada.
     *
     * @param rawPassword     senha em texto plano
     * @param encodedPassword senha codificada armazenada
     * @return true se corresponde
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

    /**
     * Indica se a senha deve ser recodificada.
     *
     * @param encodedPassword senha codificada armazenada
     * @return true se precisa upgrade
     */
    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
