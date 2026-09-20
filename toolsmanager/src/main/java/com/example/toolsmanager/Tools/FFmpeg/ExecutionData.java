package com.example.toolsmanager.Tools.FFmpeg;

import java.util.List;

public class ExecutionData {

  private String toolPath;
  private List<String> command;
  private String temporaryPath;
  private String savePath;
  private String outputPath;

  public ExecutionData(String toolPath, String temporaryPath, String savePath) {
    this.toolPath = toolPath;
    this.temporaryPath = temporaryPath;
    this.savePath = savePath;
  }

  public void setToolPath(String path) {
    toolPath = path;
  }

  public void setCommand(List<String> command) {
    this.command = command;
  }

  public void setTemporaryPath(String path) {
    temporaryPath = path;
  }

  public void setSavePath(String path) {
    savePath = path;
  }

  public void setOutputPath(String output) {
    outputPath = output;
  }

  public String getToolPath() {
    return toolPath;
  }

  public List<String> getCommand() {
    return command;
  }

  public String getTemporaryPath() {
    return temporaryPath;
  }

  public String getSavePath() {
    return savePath;
  }

  public String getOutputPath() {
    return outputPath;
  }
}
