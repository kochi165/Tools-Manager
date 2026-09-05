package com.example.toolsmanager.Account;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AccountService {

  AccountRepository repository;

  PasswordEncoder passwordEncoder;

  // コンストラクタ
  AccountService(AccountRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  // 新規登録処理
  public boolean register(AccountData data) {
    String name = data.getUsername();

    if (repository.findByUsername(name) != null) {
      return false;
    }

    String passHash = passwordEncoder.encode(data.getPassword());

    Account account = new Account();
    account.setUsername(name);
    account.setPasswordHash(passHash);

    repository.save(account);

    return true;
  }
}
