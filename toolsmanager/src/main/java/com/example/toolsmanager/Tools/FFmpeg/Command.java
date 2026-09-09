package com.example.toolsmanager.Tools.FFmpeg;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "ffmpeg_command")
public class Command {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String type;
  private String commandOption;
  private String command;
  private int sortOrder;

  public void setType(String type) {
    this.type = type;
  }

  public void setCommandOption(String commandOption) {
    this.commandOption = commandOption;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getType() {
    return type;
  }

  public String getCommandOption() {
    return commandOption;
  }

  public String getCommand() {
    return command;
  }

  public int getSortOrder() {
    return sortOrder;
  }
}