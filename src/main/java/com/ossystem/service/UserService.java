package com.ossystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ossystem.entity.User;

public interface UserService extends UserDetailsService {
  void addUser(User user);

  User getUserById(int id);
}
