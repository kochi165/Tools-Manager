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
    Map<String, String> outputOption = (Map<String, String>) commandMap.get("output");

    // フロントからの情報
    List<List<String>> order = new ArrayList<>();

    // 統合
    for (Map.Entry<String, String> entry : selectData.entrySet()) {
      order.add(List.of(entry.getKey(), entry.getValue()));
    }
    for (Map.Entry<String, String> entry : outputOption.entrySet()) {
      order.add(List.of(entry.getKey(), entry.getValue()));
    }

    List<Command> commands = new ArrayList<>();

    for (List<String> currentOrder : order) {

      String key = currentOrder.get(0);
      String value = currentOrder.get(1);

      List<Command> type = new ArrayList<>();
      List<Command> option = new ArrayList<>();

      switch (key) {

        // 処理形式判別
        case "video-format":
          type.addAll(repository.findByTypeAndCommandOption("video-codec", "codec"));

          type.addAll(repository.findByTypeAndCommandOption("audio-codec", "codec"));

          switch (value) {

            // 詳細コマンド
            case "mp4":
              option.addAll(repository.findByTypeAndCommandOption("video-codec", "mp4"));

              option.addAll(repository.findByTypeAndCommandOption("audio-codec", "aac"));

              option.addAll(repository.findByTypeAndCommandOption("video-output", "mp4"));

              break;

            default:
              break;
          }
          break;

        // 出力オプション
        case "mp4":
          type = repository.findByTypeAndCommandOption("video-crf", "constant-rate-factor");

          switch (value) {

            // 詳細コマンド
            case "high-quality":
              option = repository.findByTypeAndCommandOption("video-crf", "high");
              break;

            default:
              break;
          }
          break;

        default:
          break;
      }

      if (type.isEmpty() || option.isEmpty()) {
        return false;
      }

      commands.addAll(type);
      commands.addAll(option);
    }

    if (commands.isEmpty()) {
      return false;
    }

    commands.sort(Comparator.comparingInt(Command::getSortOrder));

    List<String> command = new ArrayList<String>();
    command.add(toolPath);
    command.add("-i");
    command.add(filePath);

    int i = 1;
    for (Command phrase : commands) {
      if (i != commands.size()) {

        command.add(phrase.getCommand());
      } else if (i == commands.size()) {

        String output = System.currentTimeMillis() + "_" + phrase.getCommand();

        command.add(output);

        path.setOutputPath(savePath + "/" + output);
      }
      i++;
    }

    executionData.setCommand(command);
    executionData.setPath(path);

    System.out.println(executionData.getCommand() + "実行前コマンド");
    System.out.println(executionData.getPath() + "実行前パス");

    Result result = executor.execute(executionData);

    return result.getCorrection();
  }
}