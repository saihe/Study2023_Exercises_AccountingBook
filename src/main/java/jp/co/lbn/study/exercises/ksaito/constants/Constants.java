package jp.co.lbn.study.exercises.ksaito.constants;

import java.util.UUID;
import java.util.stream.Stream;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * 定数インターフェース.

 * @param <E> .
 */
public sealed interface Constants<E extends Enum<E>> permits AccountingSubject, AccountingType {
  UUID getId();

  int getCode();

  String getName();

  @SuppressWarnings("unchecked")
  default E toEnum() {
    return (E) this;
  }

  /**
   * IDからenumを取得する.

   * @param clazz 取得するenumのクラス.
   * @param id type UUID.
   * @return .
   * @throws AppException .
   */
  static <E extends Enum<E>> E getById(
      Class<? extends Constants<E>> clazz,
      UUID id
  ) throws AppException {
    return Stream.of(clazz.getEnumConstants())
        .filter(e -> e.getId().equals(id))
        .findFirst()
        .map(Constants::toEnum)
        .orElseThrow(AppException::internal);
  }

  /**
   * codeからenumを取得する.

   * @param clazz 取得するenumのクラス.
   * @param code .
   * @return .
   * @throws AppException .
   */
  static <E extends Enum<E>> E getByCode(
      Class<? extends Constants<E>> clazz,
      int code
  ) throws AppException {
    return Stream.of(clazz.getEnumConstants())
        .filter(e -> e.getCode() == code)
        .findFirst()
        .map(Constants::toEnum)
        .orElseThrow(AppException::internal);
  }
}
