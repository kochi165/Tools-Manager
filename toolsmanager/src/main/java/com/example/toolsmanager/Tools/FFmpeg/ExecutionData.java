package com.example.toolsmanager.Tools.FFmpeg;

import java.util.List;

public class ExecutionData {

  private List<String> command;
  private Path path;

  public void setCommand(List<String> command) {
    this.command = command;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public List<String> getCommand() {
    return command;
  }

  public Path getPath() {
    return path;
  }
}
