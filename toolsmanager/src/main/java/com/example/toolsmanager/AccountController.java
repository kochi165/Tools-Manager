package com.example.toolsmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accountController")
public class AccountController {

  @PostMapping("/login")
  void login(@RequestBody AccountData userdata) {
    userdata.getUsername();
    userdata.getPassword();
  }

  @PostMapping("/register")
  void register(@RequestBody AccountData userdata) {
    userdata.getUsername();
    userdata.getPassword();
  }
}