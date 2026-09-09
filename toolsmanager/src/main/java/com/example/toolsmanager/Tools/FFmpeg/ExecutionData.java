package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.web.multipart.MultipartFile;

public class ExecutionData {

  private String command;

  private MultipartFile file;

  public void setCommand(String command) {
    this.command = command;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public String getCommand() {
    return command;
  }

  public MultipartFile getFile() {
    return file;
  }

}
