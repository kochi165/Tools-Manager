package com.example.toolsmanager.Tools.FFmpeg;

public class Result {

  private boolean correction;
  private String filePath;

  public void setCorrection(boolean bool) {
    correction = bool;
  }

  public void setFilePath(String path) {
    filePath = path;
  }

  public boolean getCorrection() {
    return correction;
  }

  public String getFilePath() {
    return filePath;
  }
}
