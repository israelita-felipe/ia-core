package com.ia.core.security.service.user;

import com.ia.core.security.model.user.User;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository
  extends BaseEntityRepository<User> {

  @Query("select u from User u where u.userCode = :userCode")
  User findByUserCode(@Param("userCode") String userCode);
}
