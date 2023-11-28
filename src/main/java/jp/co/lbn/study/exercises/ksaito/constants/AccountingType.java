package jp.co.lbn.study.exercises.ksaito.constants;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 収支区分.
 */
@AllArgsConstructor
@Getter
public enum AccountingType implements Constants<AccountingType> {
  /** 収入. */
  REVENUE(UUID.fromString("2d6b318d-141e-4cfd-ab66-fdef657299cb"), 1, "収入"),
  /** 支出. */
  EXPENDITURE(UUID.fromString("e3c79687-f082-4d3e-abfa-b922151b4080"), 2, "支出");

  private final UUID id;
  private final int code;
  private final String name;
}
