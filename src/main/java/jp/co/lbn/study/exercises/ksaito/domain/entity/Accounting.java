package jp.co.lbn.study.exercises.ksaito.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingSubject;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingType;
import jp.co.lbn.study.exercises.ksaito.constants.Constants;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 収支.
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public non-sealed class Accounting extends AbstractEntity {
  /** ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  /** 年. */
  private String years;
  /** 月. */
  private String months;
  /** 日. */
  private String dates;
  /** 収支区分. */
  private UUID accountingTypeId;
  /** 科目. */
  private UUID accountingSubjectId;
  /** 金額. */
  private Long amount;

  /**
   * フィールドの値を再編集するためにビルダーを返す.

   * @return .
   */
  public AccountingBuilder<?, ?> cloneBuilder() {
    return Accounting.builder()
        .id(id)
        .years(years)
        .months(months)
        .dates(dates)
        .accountingTypeId(accountingTypeId)
        .accountingSubjectId(accountingSubjectId)
        .amount(amount)
        .createdAt(createdAt);
  }

  public String getAccountingTypeDisplayName() throws AppException {
    var e = Constants.getById(AccountingType.class, accountingTypeId);
    return e.getCode() + "：" + e.getName();
  }

  public String getAccountingSubjectDisplayName() throws AppException {
    var e = Constants.getById(AccountingSubject.class, accountingSubjectId);
    return e.getCode() + "：" + e.getName();
  }
}
