package com.example.toolsmanager.Tools.FFmpeg;

import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class Executor {

  public Result execute(ExecutionData data) throws Exception {

    ProcessBuilder pb = new ProcessBuilder();

    Result result = new Result();

    pb.command(data.getCommand());

    pb.redirectError(ProcessBuilder.Redirect.INHERIT);

    Process process = pb.start();
    int ret = process.waitFor();

    File file = new File(data.getOutputPath());

    if (ret == 0) {
      result.setCorrection(true);
      result.setConvertedFile(file);
    } else {
      result.setCorrection(false);
    }

    return result;
  }
}
