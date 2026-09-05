package com.example.toolsmanager.Account;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class AccountUserDetailsService implements UserDetailsService {

  private final AccountRepository repository;

  public AccountUserDetailsService(AccountRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

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