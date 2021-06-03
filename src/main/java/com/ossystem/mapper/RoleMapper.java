package com.ossystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ossystem.entity.Role;

@Mapper
public interface RoleMapper {
  Role selectRoleById(@Param("id") int id);
}
