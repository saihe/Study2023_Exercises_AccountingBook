package jp.co.lbn.study.exercises.ksaito.constants;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 科目.
 */
@AllArgsConstructor
@Getter
public enum AccountingSubject implements Constants<AccountingSubject> {
  /** 交通費. */
  TRANSPORTATION_EXPENSES(UUID.fromString("c86a5f86-e147-4df4-bd44-88084d070f13"), 1, "交通費"),
  /** 食費. */
  FOOD_EXPENSES(UUID.fromString("5ebb50b9-9a50-473b-9ab5-2f25ccd2b4f9"), 2, "食費"),
  /** 雑費. */
  MISCELLANEOUS_EXPENSES(UUID.fromString("cb5eaf06-b980-4b41-891e-f19d72c1b651"), 3, "雑費");

  private final UUID id;
  private final int code;
  private final String name;
}
