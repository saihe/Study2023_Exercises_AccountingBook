package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingSubject;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingType;
import jp.co.lbn.study.exercises.ksaito.constants.Constants;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.domain.repository.Repository;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import jp.co.lbn.study.exercises.ksaito.infractructure.AccountingRepository;

/**
 * 収支検索画面.
 */
public class SearchView extends View {
  private final Repository<Accounting> repository;

  /**
   * コンストラクタ.
   */
  public SearchView() {
    super("収支検索\t検索条件番号を入力後に検索条件を入力、空Enterで検索して検索。q を入力してホーム画面へ遷移。");
    contents = List.of(
        new Row("#", col1("検索条件対象"), col2(0, "検索条件")),
        new Row("1", col1("年（yyyy）"), col2(1, "____")),
        new Row("2", col1("月（m or mm）"), col2(2, "__")),
        new Row("3", col1("日（d or dd）"), col2(3, "__")),
        new Row("4", col1("収支区分（1: 収入, 2: 支出）"), col2(4, "_")),
        new Row("5", col1("科目（1: 交通費, 2: 食費, 3: 雑費）"), col2(5, "_")),
        new Row("6", col1("金額"), col2(6, "__________"))
    );
    repository = new AccountingRepository();
  }

  private String col1(String input) {
    return String.format("\t%s", input);
  }

  private String col2(int rowNumber, String input) {
    return switch (rowNumber) {
      case 1, 2, 3 -> "\t\t\t\t" + input;
      case 4 -> "\t\t" + input;
      case 6 -> "\t\t\t\t\t" + input;
      default -> "\t" + input;
    };
  }

  @Override
  public void process(BufferedReader br) throws AppException {
    var isSetData = false;
    var selector = 0;
    try {
      var accountingBuilder = Accounting.builder();
      while (true) {
        draw();
        var selected = br.readLine();
        if (!isSetData) {
          if (selected.equals("q")) {
            nextView = new HomeView();
            break;
          }
          if (selected.isEmpty()) {
            nextView = new ListView(repository.find(accountingBuilder.build()));
            break;
          }
          if (selected.matches("[0-6]")) {
            selector = Integer.parseInt(selected);
            isSetData = true;
            continue;
          }
          continue;
        }
        isSetData = false;
        contents.get(selector).getColumns().get(2).setDisplayValue(col2(selector, selected));
        switch (selector) {
          case 1 -> accountingBuilder.years(selected);
          case 2 -> accountingBuilder.months(selected);
          case 3 -> accountingBuilder.dates(selected);
          case 4 -> accountingBuilder.accountingTypeId(
              Constants.getByCode(AccountingType.class, Integer.parseInt(selected)).getId());
          case 5 -> accountingBuilder.accountingSubjectId(
              Constants.getByCode(AccountingSubject.class, Integer.parseInt(selected)).getId());
          case 6 -> accountingBuilder.amount(Long.parseLong(selected));
          default -> {
          }
        }
      }
    } catch (IOException e) {
      throw new AppException(e);
    }
  }
}
