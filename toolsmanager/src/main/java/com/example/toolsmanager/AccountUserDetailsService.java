package com.example.toolsmanager;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class AccountUserDetailsService implements UserDetailsService {

  private final AccountRepository repository;

  public AccountUserDetailsService(AccountRepository repository) {
    System.out.println("届いてます3");
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    System.out.println("届いてますー");

    Account account = repository.findByUsername(username);

    if (account == null) {
      throw new UsernameNotFoundException("NotFound");
    }
    return User
        .withUsername(account.getUsername())
        .password(account.getPasswordHash())
        // ダミー
        .authorities("USER")
        .build();
  }

}