package jp.co.lbn.study.exercises.ksaito;

import jp.co.lbn.study.exercises.ksaito.config.AppConfig;
import jp.co.lbn.study.exercises.ksaito.controller.ViewController;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import jp.co.lbn.study.exercises.ksaito.migration.Migration;

/**
 * お小遣い出納帳アプリ.
 */
public class App {
  /**
   * エントリーポイント.
   */
  public static void main(String[] args) {
    AppConfig.load();
    try {
      new Migration().migrate();
      new ViewController().control();
    } catch (AppException e) {
      throw new RuntimeException(e);
    }
  }
}
