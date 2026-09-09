package com.example.toolsmanager.Tools.FFmpeg;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer> {

  List<Command> findByTypeAndCommandOption(
      String type,
      String commandOption);

}
