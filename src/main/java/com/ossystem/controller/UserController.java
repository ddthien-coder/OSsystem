package com.ossystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ossystem.entity.Response;
import com.ossystem.entity.User;
import com.ossystem.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/auth")
  public Response getAuthentication(@AuthenticationPrincipal User user) {
    user.setPassword(null);
    return new Response("success", user);
  }

  @GetMapping("/user/{id}")
  public Response getUser(@PathVariable("id") int id) {
    User user = userService.getUserById(id);
    if (user.getId() == 0) {
      return new Response("error", "This user does not exist!");
    }
    return new Response("success", user);
  }
}
