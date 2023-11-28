package jp.co.lbn.study.exercises.ksaito.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * 行.
 */
@Getter
public class Row {
  private final List<Column> columns;

  /**
   * コンストラクタ.
   */
  public Row() {
    columns = new ArrayList<>();
  }

  /**
   * コンストラクタ.
   */
  public Row(String... cols) {
    columns = new ArrayList<>();
    for (var col : cols) {
      columns.add(new Column(col));
    }
  }

  /**
   * 表示用.
   */
  public String toString() {
    return columns.stream().map(Column::getDisplayValue).collect(Collectors.joining());
  }
}
