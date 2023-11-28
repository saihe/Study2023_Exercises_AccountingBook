package jp.co.lbn.study.exercises.ksaito.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * アプリ設定.
 * 階層構造は持たないようにする
 */
public class AppConfig {

  /**
   * アプリ設定を.envから読み込み、ただし.envファイルがない場合でもエラーにはしない
   */
  public static void load() {
    try {
      List<String> envFile = Files.readAllLines(Paths.get(".", ".env"), StandardCharsets.UTF_8);
      envFile.stream()
          .map(e -> e.split("="))
          .forEach(arr -> System.setProperty(arr[0], arr[1]));
    } catch (IOException e) {
      // .envファイルがなくても何も設定しないだけでエラーにはしない
    }
  }
}
