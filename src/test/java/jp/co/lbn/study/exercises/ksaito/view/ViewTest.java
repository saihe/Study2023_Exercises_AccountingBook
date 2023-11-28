package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import jp.co.lbn.study.exercises.ksaito.exception.AppException;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
  private static class Stub extends View {
    Stub() {
      super("テスト");
    }

    @Override
    public void process(BufferedReader br) throws AppException {
      nextView = new HomeView();
    }
  }

  private final View target = new Stub();

  @Test
  void draw() {
    assertDoesNotThrow(() -> {
      var out = new ByteArrayOutputStream();
      System.setOut(new PrintStream(out));
      target.contents = List.of(new Row("あ"), new Row("い"));
      target.draw();
      assertLinesMatch(
        List.of("\033[H\033[2Jテスト", "\tあ", "\tい", "Enterキーで入力を確定する", "入力："),
        List.of(out.toString().split(System.lineSeparator()))
      );
    });
  }

  @Test
  void nextView() {
    assertDoesNotThrow(() -> {
      target.process(null);
      assertEquals(HomeView.class, target.nextView().getClass());
    });
  }
}