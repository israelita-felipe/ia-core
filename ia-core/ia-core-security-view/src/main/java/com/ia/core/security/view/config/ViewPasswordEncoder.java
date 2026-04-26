package com.ia.core.security.view.config;

import com.ia.core.security.service.model.user.UserPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Israel Araújo
 */
public class ViewPasswordEncoder
  extends BCryptPasswordEncoder
  implements UserPasswordEncoder {

}
