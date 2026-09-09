package com.example.toolsmanager.Tools;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import com.example.toolsmanager.Tools.FFmpeg.SelectedData;
import com.example.toolsmanager.Tools.FFmpeg.CommandService;

@RestController
@RequestMapping("/toolControl")
public class ToolController {

  CommandService service;

  ToolController(CommandService service) {
    this.service = service;
  }

  @PostMapping("/ffmpeg")
  void ffmpeg(
      @RequestPart("command") String command,
      @RequestPart("file") MultipartFile file) {

    SelectedData data = new SelectedData();
    data.setCommandMap(command);
    data.setFile(file);

    System.out.println(data.getCommandMap());
    System.out.println(data.getFile());

    service.process(data);
  }

}
