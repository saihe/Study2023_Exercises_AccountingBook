package jp.co.lbn.study.exercises.ksaito.domain.repository;

import java.util.List;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * リポジトリインターフェース.

 * @param <T> エンティティクラス.
 */
public interface Repository<T> {
  List<T> find(T condition);

  void create(T entity) throws AppException;

  void modify(T entity) throws AppException;
}
