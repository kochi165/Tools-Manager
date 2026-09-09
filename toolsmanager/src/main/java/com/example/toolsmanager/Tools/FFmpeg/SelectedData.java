package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class SelectedData {

  private final JacksonJsonParser parser = new JacksonJsonParser();

  private Map<String, Object> commandMap;
  private MultipartFile file;

  public void setCommandMap(String command) {
    commandMap = parser.parseMap(command);
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public Map<String, Object> getCommandMap() {
    return commandMap;
  }

  public MultipartFile getFile() {
    return file;
  }

}
