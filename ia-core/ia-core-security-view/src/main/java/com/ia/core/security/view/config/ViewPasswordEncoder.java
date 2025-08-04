package com.ia.core.security.view.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ia.core.security.service.model.user.UserPasswordEncoder;

/**
 * @author Israel Araújo
 */
public class ViewPasswordEncoder
  extends BCryptPasswordEncoder
  implements UserPasswordEncoder {

}
