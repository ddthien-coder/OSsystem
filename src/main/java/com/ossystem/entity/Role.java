package com.ossystem.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

@Alias("Role")
@Entity
public class Role {
  private int    id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
