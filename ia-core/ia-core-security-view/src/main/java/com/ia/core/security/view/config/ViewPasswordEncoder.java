package com.ia.core.security.view.config;

import com.ia.core.security.service.model.user.UserPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Componente de interface visual para view password encoder.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ViewPasswordEncoder
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class ViewPasswordEncoder
  extends BCryptPasswordEncoder
  implements UserPasswordEncoder {

}
