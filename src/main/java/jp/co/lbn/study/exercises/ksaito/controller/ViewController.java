package jp.co.lbn.study.exercises.ksaito.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import jp.co.lbn.study.exercises.ksaito.view.HomeView;
import jp.co.lbn.study.exercises.ksaito.view.View;

/**
 * 画面コントローラー.
 */
public class ViewController {
  /**
   * メイン処理コントロール.

   * @throws AppException .
   */
  public void control() throws AppException {
    try (var br = new BufferedReader(new InputStreamReader(System.in))) {
      // 最初はホーム画面を表示する
      View currentView = new HomeView();

      // 以降は無限ループ
      while (true) {
        try {
          currentView.process(br);
        } catch (AppException ae) {
          System.out.println("Enterキーで続行...");
          br.readLine();
          System.out.println(ae.getMessage());
        }
        if (Objects.isNull(currentView.nextView())) {
          break;
        }
        currentView = currentView.nextView();
      }
      System.out.println("アプリケーションを終了します。");
      System.exit(0);
    } catch (IOException e) {
      throw new AppException(e);
    }
  }
}
