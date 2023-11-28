package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.util.List;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * 収支編集画面.
 */
public class EditView extends View {
  public EditView(Accounting entity) {
    super("収支編集");
    contents = List.of(new Row());
  }

  @Override
  public void process(BufferedReader br) throws AppException {

  }
}
