package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

@Service
@SuppressWarnings("unchecked")
public class CommandService {

  CommandRepository repository;
  Executor executor;

  CommandService(CommandRepository repository, Executor executor) {
    this.repository = repository;
    this.executor = executor;
  }

  @Value("${tools.ffmpeg.tool.path}")
  private String toolPath;

  @Value("${tools.ffmpeg.temporary.path}")
  private String temporaryPath;

  @Value("${tools.ffmpeg.save.path}")
  private String savePath;

  public boolean process(SelectedData data) throws Exception {

    Path path = new Path(toolPath, temporaryPath, savePath);

    ExecutionData executionData = new ExecutionData();

    Map<String, Object> commandMap = data.getCommandMap();
    MultipartFile file = data.getFile();

    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

    String filePath = temporaryPath + fileName;

    File dest = new File(filePath);
    file.transferTo(dest);

    Map<String, String> selectData = (Map<String, String>) commandMap.get("selected");
    String outputOption = (String) commandMap.get("output");

    List<Command> commands = new ArrayList<>();
    List<Command> outputCommands = new ArrayList<>();

    for (Map.Entry<String, String> entry : selectData.entrySet()) {

      List<Command> search = repository.findByTypeAndCommandOption(entry.getKey(), entry.getValue());

      if (search.isEmpty()) {
        return false;
      }

      commands.addAll(search);
    }

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

    List<String> command = new ArrayList<String>();
    command.add(toolPath + " ");
    command.add("-i ");
    command.add(filePath);

    int i = 1;
    for (Command phrase : commands) {
      if (i != commands.size()) {

        command.add(phrase.getCommand() + " ");
      } else if (i == commands.size()) {

        String output = System.currentTimeMillis() + "_" + phrase.getCommand();

        command.add(output);

        path.setOutputPath(savePath + "/" + output);
      }
      i++;
    }

    executionData.setCommand(command);
    executionData.setPath(path);

    System.out.println(executionData.getCommand());
    System.out.println(executionData.getPath());

    Result result = executor.execute(executionData);

    return result.getCorrection();
  }
}