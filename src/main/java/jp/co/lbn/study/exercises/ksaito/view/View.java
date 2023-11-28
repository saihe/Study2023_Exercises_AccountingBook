package jp.co.lbn.study.exercises.ksaito.view;

import java.util.List;
import jp.co.lbn.study.exercises.ksaito.component.Row;

/**
 * 画面抽象クラス.
 */
public abstract class View implements Processor {
  private final String name;
  protected View nextView;
  protected List<Row> contents;

  protected View(String name) {
    this.name = name;
  }

  /**
   * 描画.
   */
  public void draw() {
    System.out.print("\033[H\033[2J");
    System.out.println(name);
    contents.stream().map(c -> String.format("\t%s", c)).forEach(System.out::println);
    System.out.println("Enterキーで入力を確定する");
    System.out.print("入力：");
    System.out.flush();
  }

  /**
   * 次の遷移先画面番号を返却.
   *
   * @return .
   */
  @Override
  public View nextView() {
    return nextView;
  }

}
