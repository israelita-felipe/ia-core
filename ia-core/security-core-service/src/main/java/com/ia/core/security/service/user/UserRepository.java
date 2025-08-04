package com.ia.core.security.service.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.security.model.user.User;
import com.ia.core.service.repository.BaseEntityRepository;

public interface UserRepository
  extends BaseEntityRepository<User> {

  @Query("select u from User u where u.userCode = :userCode")
  User findByUserCode(@Param("userCode") String userCode);
}
