package com.ossystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossystem.entity.Role;

@Mapper
public interface RoleMapper {
  void insertRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);

  int deleteRoleFromUser(@Param("userId") int userId, @Param("roleId") int roleId);

  List<Role> selectRolesByUserId(@Param("userId") int userId);
}
