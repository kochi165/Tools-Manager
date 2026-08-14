package com.example.toolsmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;
  private String passwordHash;

  public void setUsername(String name) {
    username = name;
  }

  public void setPasswordHash(String passHash) {
    passwordHash = passHash;
  }

  public String getUsername() {
    return username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}