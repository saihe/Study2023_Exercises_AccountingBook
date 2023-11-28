package jp.co.lbn.study.exercises.ksaito.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {
  private final Path envFilePath = Paths.get(".env");

  @BeforeEach
  void setUp() throws IOException {
    Files.createTempFile(envFilePath.toString(), "");
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(envFilePath);
  }

  @Test
  void load() {
    assertDoesNotThrow(() -> {
      var lines = List.of("val1=value1", "val2=value2", "val3=value3");
      Files.write(envFilePath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      AppConfig.load();
      assertAll(
        () -> assertEquals("value1", System.getProperty("val1")),
        () -> assertEquals("value2", System.getProperty("val2")),
        () -> assertEquals("value3", System.getProperty("val3"))
      );
    });
  }
}