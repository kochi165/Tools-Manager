package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

@Service
public class CommandService {

  CommandRepository repository;

  CommandService(CommandRepository repository) {
    this.repository = repository;
  }

  public boolean process(SelectedData data) {

    Map<String, Object> commandMap = data.getCommandMap();
    MultipartFile file = data.getFile();

    Map<String, String> selectData = (Map<String, String>) commandMap.get("selected");
    String outputOption = (String) commandMap.get("output");

    List<Command> commands = new ArrayList<>();
    List<Command> outputCommands;

    for (Map.Entry<String, String> entry : selectData.entrySet()) {

      List<Command> search = repository.findByTypeAndCommandOption(entry.getKey(), entry.getValue());

      if (search.isEmpty()) {
        return false;
      }

      commands.addAll(search);
    }

    outputCommands = new ArrayList<>();

    for (Command command : commands) {

      if (command.getType().contains("video")) {

        outputCommands = repository.findByTypeAndCommandOption("video_output", outputOption);
        break;

      } else if (command.getType().contains("audio")) {

        outputCommands = repository.findByTypeAndCommandOption("audio_output", outputOption);
        break;

      } else if (command.getType().contains("image")) {

        outputCommands = repository.findByTypeAndCommandOption("image_output", outputOption);
        break;
      }
    }

    if (outputCommands.isEmpty()) {
      return false;
    }

    commands.addAll(outputCommands);

    commands.sort(Comparator.comparingInt(Command::getSortOrder));

    StringBuilder command = new StringBuilder();

    ExecutionData executionData = new ExecutionData();

    for (Command phrase : commands) {
      command.append(phrase.getCommand());
    }

    executionData.setCommand(command.toString());
    executionData.setFile(file);

    return true;
  }
}