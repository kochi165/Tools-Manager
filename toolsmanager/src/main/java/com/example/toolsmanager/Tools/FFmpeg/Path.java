package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.beans.factory.annotation.Value;

public class Path {

  @Value("${tools.ffmpeg.tool.path}")
  private String ffmpeg;
  @Value("${tools.ffmpeg.temporary.path}")
  private String temporaryPath;
  @Value("${tools.ffmpeg.save.path}")
  private String savaPath;
  private String outputPath;

  public void setOutputPath(String path) {
    outputPath = path;
  }

  public String getFFmpegPath() {
    return ffmpeg;
  }

  public String getTemporaryPath() {
    return temporaryPath;
  }

  public String getSavePath() {
    return savaPath;
  }

  public String getOutputPath() {
    return outputPath;
  }
}
