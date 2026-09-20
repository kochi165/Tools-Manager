package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.stereotype.Component;

@Component
public class Executor {

  public Result execute(ExecutionData data) throws Exception {

    ProcessBuilder pb = new ProcessBuilder();

    Result result = new Result();

    String outputPath = data.getPath().getOutputPath();

    pb.command(data.getCommand());

    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

    Process process = pb.start();
    int ret = process.waitFor();

    if (ret == 0) {
      result.setCorrection(true);
      result.setFilePath(outputPath);
    } else {
      result.setCorrection(false);
      result.setFilePath(null);
    }

    return result;
  }
}
