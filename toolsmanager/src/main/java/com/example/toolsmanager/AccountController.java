package com.example.toolsmanager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/accountController")
public class AccountController {

  AccountService service;

  AccountController(AccountService service) {
    this.service = service;
  }

  @PostMapping("/register")
  boolean register(@RequestBody AccountData data) {
    return service.register(data);
  }
}