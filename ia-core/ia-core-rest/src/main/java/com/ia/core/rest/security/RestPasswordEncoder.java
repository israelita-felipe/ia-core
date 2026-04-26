package com.ia.core.rest.security;

import com.ia.core.security.service.model.user.UserPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Implementação de codificador de senhas usando BCrypt.
 * <p>
 * Utiliza o algoritmo BCrypt para hash de senhas, que inclui
 * salt automático e custo computacional configurável.
 *
 * @author Israel Araújo
 * @see BCryptPasswordEncoder
 */
public class RestPasswordEncoder
  extends BCryptPasswordEncoder
  implements UserPasswordEncoder {

}
