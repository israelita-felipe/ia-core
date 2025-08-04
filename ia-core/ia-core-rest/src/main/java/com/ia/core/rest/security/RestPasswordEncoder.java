package com.ia.core.rest.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ia.core.security.service.model.user.UserPasswordEncoder;

/**
 * @author Israel Araújo
 */
public class RestPasswordEncoder
  extends BCryptPasswordEncoder
  implements UserPasswordEncoder {

}
