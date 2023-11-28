package jp.co.lbn.study.exercises.ksaito.infractructure;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.domain.repository.Repository;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * 収支リポジトリ.
 */
public class AccountingRepository extends DataBase implements Repository<Accounting> {
  @Override
  public List<Accounting> find(Accounting condition) {
    var cb = criteriaBuilder();
    var sql = cb.createQuery(Accounting.class);
    var table = sql.from(Accounting.class);
    List<Predicate> predicates = new ArrayList<>();
    if (Objects.nonNull(condition.getId()) && !condition.getId().toString().isBlank()) {
      predicates.add(cb.equal(table.get("id"), condition.getId()));
    }
    if (Objects.nonNull(condition.getYears()) && !condition.getYears().isBlank()) {
      predicates.add(cb.equal(table.get("years"), condition.getYears()));
    }
    if (Objects.nonNull(condition.getMonths()) && !condition.getMonths().isBlank()) {
      predicates.add(cb.equal(table.get("months"), condition.getMonths()));
    }
    if (Objects.nonNull(condition.getDates()) && !condition.getDates().isBlank()) {
      predicates.add(cb.equal(table.get("dates"), condition.getDates()));
    }
    if (Objects.nonNull(condition.getAccountingTypeId())) {
      predicates.add(cb.equal(table.get("accountingTypeId"), condition.getAccountingTypeId()));
    }
    if (Objects.nonNull(condition.getAccountingSubjectId())) {
      predicates.add(
          cb.equal(table.get("accountingSubjectId"), condition.getAccountingSubjectId()));
    }
    if (Objects.nonNull(condition.getAmount())) {
      predicates.add(cb.equal(table.get("amount"), condition.getAmount()));
    }
    sql.select(table).where(predicates.toArray(Predicate[]::new));

    return query(sql).getResultList();
  }

  @Override
  public void create(Accounting entity) throws AppException {
    transaction(tx -> tx.persist(entity));
  }

  @Override
  public void modify(Accounting entity) throws AppException {
    transaction(tx -> tx.merge(entity));
  }
}
