package com.example.toolsmanager.Tools.FFmpeg;

import java.io.File;

public class Result {

  private boolean correction;
  private File convertedFile;

  public void setCorrection(boolean bool) {
    correction = bool;
  }

  public void setConvertedFile(File file) {
    convertedFile = file;
  }

  public boolean getCorrection() {
    return correction;
  }

  public File getConvertedFile() {
    return convertedFile;
  }
}
