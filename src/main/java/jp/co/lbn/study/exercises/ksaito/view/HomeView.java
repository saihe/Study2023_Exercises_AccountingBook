package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * ホーム画面.
 */
public class HomeView extends View {
  /**
   * コンストラクタ.
   */
  public HomeView() {
    super("お小遣い出納帳アプリ");
    contents = List.of(
      new Row("メニュー"),
      new Row("\t1", "\t収支入力"),
      new Row("\t2", "\t収支検索"),
      new Row("\tq", "\tアプリケーションを終了")
    );
  }

  @Override
  public void process(BufferedReader br) throws AppException {
    draw();
    try {
      nextView = switch (br.readLine()) {
        case "1" -> new InputView();
        case "2" -> new SearchView();
        case "q" -> null;
        default -> new HomeView();
      };
    } catch (IOException e) {
      throw new AppException(e);
    }
  }
}
