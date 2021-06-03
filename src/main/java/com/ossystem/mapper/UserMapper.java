package com.ossystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossystem.entity.User;

@Mapper
public interface UserMapper {
  int insertUser(User user);

  int deleteUser(@Param("id") int id);

  User selectUserById(@Param("Id") int Id);

  User selectUserByEmail(@Param("email") String email);

  User selectUserByUsername(@Param("username") String username);
}