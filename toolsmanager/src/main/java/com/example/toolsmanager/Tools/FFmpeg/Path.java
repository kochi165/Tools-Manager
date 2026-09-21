package com.example.toolsmanager.Tools.FFmpeg;

public class Path {

  private String toolPath;
  private String temporaryPath;
  private String savePath;
  private String outputPath;

  public Path(String toolPath, String temporaryPath, String savePath) {
    this.toolPath = toolPath;
    this.temporaryPath = temporaryPath;
    this.savePath = savePath;
  }

  public void setOutputPath(String path) {
    outputPath = path;
  }

  public String getToolPath() {
    return toolPath;
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
