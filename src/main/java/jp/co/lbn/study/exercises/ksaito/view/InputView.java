package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import jp.co.lbn.study.exercises.ksaito.component.Row;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingSubject;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingType;
import jp.co.lbn.study.exercises.ksaito.constants.Constants;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.domain.repository.Repository;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import jp.co.lbn.study.exercises.ksaito.infractructure.AccountingRepository;

/**
 * 収支入力画面.
 */
public class InputView extends View {
  private final Repository<Accounting> repository;
  private final Row[] inputParams;

  /**
   * コンストラクタ.
   */
  public InputView() {
    super("収支入力");
    contents = new ArrayList<>();
    inputParams = new Row[] {
      new Row("年（yyyy）", col(1, "____")),
      new Row("月（m or mm）", col(2, "__")),
      new Row("日（d or dd）", col(3, "__")),
      new Row("収支区分（1: 収入、2: 支出）", col(4, "_")),
      new Row("科目（1: 交通費、2: 食費、3: 雑費）", col(5, "_")),
      new Row("金額", col(6, "__________"))
    };
    repository = new AccountingRepository();
  }

  private String col(int rowNumber, String input) {
    return switch (rowNumber) {
      case 1, 2, 3 -> "\t\t\t\t" + input;
      case 4 -> "\t\t" + input;
      case 5 -> "\t" + input;
      case 6 -> "\t\t\t\t\t" + input;
      default -> input;
    };
  }

  @Override
  public void process(BufferedReader br) throws AppException {
    var accountingBuilder = Accounting.builder();
    try {
      for (var number = 0; number < inputParams.length; number++) {
        contents.add(inputParams[number]);
        draw();
        var input = br.readLine();
        contents.get(number).getColumns().get(1).setDisplayValue(col(number + 1, input));
        draw();
        switch (number + 1) {
          case 1 -> accountingBuilder.years(String.format("%04d", Integer.parseInt(input)));
          case 2 -> accountingBuilder.months(String.format("%02d", Integer.parseInt(input)));
          case 3 -> accountingBuilder.dates(String.format("%02d", Integer.parseInt(input)));
          case 4 -> accountingBuilder.accountingTypeId(
              Constants.getByCode(AccountingType.class, Integer.parseInt(input)).getId());
          case 5 -> accountingBuilder.accountingSubjectId(
              Constants.getByCode(AccountingSubject.class, Integer.parseInt(input)).getId());
          case 6 -> accountingBuilder.amount(Long.parseLong(input));
          default -> {
          }
        }
      }
      System.out.println("Enterキーで保存");
      br.readLine();
      repository.create(accountingBuilder.build());
    } catch (IOException e) {
      nextView = new InputView();
      throw new AppException(e);
    }
    nextView = new HomeView();
  }
}
