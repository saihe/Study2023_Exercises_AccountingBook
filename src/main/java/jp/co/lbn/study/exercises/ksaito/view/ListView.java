package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * 検索結果表示画面.
 */
public class ListView extends View {
  private final List<Accounting> searchResult;

  protected ListView(List<Accounting> accountingList) {
    super("検索結果一覧\t番号を選択して編集画面へ遷移。s を入力して検索画面へ遷移。");
    contents = new ArrayList<>();
    searchResult = accountingList;
  }

  @Override
  public void process(BufferedReader br) throws AppException {
    if (searchResult.isEmpty()) {
      contents.add(new Row("検索結果が0件です。"));
    }
    for (var i = 0; i < searchResult.size(); i++) {
      contents.add(accountingToRow(i + 1, searchResult.get(i)));
    }
    draw();
    try {
      var input = br.readLine();
      if (input.matches("[0-9]")) {
        nextView = new EditView(searchResult.get(Integer.parseInt(input) - 1));
        return;
      }
      nextView = new SearchView();
    } catch (ArrayIndexOutOfBoundsException e) {
      // 何もしない
    } catch (IOException e) {
      throw new AppException(e);
    }
  }

  private Row accountingToRow(int rowNumber, Accounting src) throws AppException {
    return new Row(
      rowNumber + "\t",
      String.format("%s  %s%s%s\t%-7s%s\t%,12d円",
          src.getId(),
          src.getYears() + "年",
          src.getMonths() + "月",
          src.getDates() + "日",
          src.getAccountingTypeDisplayName(),
          src.getAccountingSubjectDisplayName(),
          src.getAmount()
      )
    );
  }
}
