package com.ossystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossystem.entity.User;

@Mapper
public interface UserMapper {
  int insertUser(User user);

  User selectUserById(@Param("id") int id);

  User selectUserByUsername(@Param("username") String username);
}