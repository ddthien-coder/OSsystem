package com.ossystem.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.ossystem.service.UserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  private final UserService     userService;

  @Autowired
  public WebSecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/api/**").authenticated().and()
        .formLogin().loginProcessingUrl("/api/login").successHandler((request, response, authentication) -> {
          response.setContentType("application/json;charset=utf-8");
          PrintWriter out = response.getWriter();
          out.write("{\"status\":\"success\",\"message\":\"login successful!\"}");
          out.flush();
          out.close();
        }).failureHandler((request, response, exception) -> {
          response.setContentType("application/json;charset=utf-8");
          PrintWriter out = response.getWriter();
          out.write("{\"status\":\"error\",\"message\":\"Account or password is wrong!\"}");
          out.flush();
          out.close();
        }).and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          PrintWriter out = response.getWriter();
          out.write("{\"status\":\"error\",\"message\":\"The authorization has expired, please log in again.\"}");
          out.flush();
          out.close();
        }).accessDeniedHandler((request, response, exception) -> {
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          PrintWriter out = response.getWriter();
          out.write("{\"status\":\"error\",\"message\":\"You do not have permission to perform this operation!\"}");
          out.flush();
          out.close();
        }).and().logout().logoutUrl("/api/logout").logoutSuccessHandler((request, response, authentication) -> {
          response.setContentType("application/json;charset=utf-8");
          PrintWriter out = response.getWriter();
          out.write("{\"status\":\"success\",\"message\":\"Logout successful!\"}");
          out.flush();
          out.close();
        }).and().rememberMe().rememberMeParameter("remember")
        .userDetailsService(userService).and().csrf().disable();
  }
}
