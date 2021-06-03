package com.ossystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ossystem.entity.Role;
import com.ossystem.entity.User;
import com.ossystem.service.UserService;

@SpringBootTest
class OSsystemApplicationTests {

  private final UserService userService;

  @Autowired
  OSsystemApplicationTests(UserService userService) {
      this.userService = userService;
  }

  @Test
  void contextLoads() {
  }

  @Test
  void addUser() {
      Role role = new Role();
      role.setId(1);
      User user = new User();
      user.setUsername("admin");
      user.setPassword("1234");
      user.setSynopsis("This is employee one");
      user.setPicture("/img/picture/4.jpg");
      user.setRole(role);
      userService.addUser(user);
  }
}
