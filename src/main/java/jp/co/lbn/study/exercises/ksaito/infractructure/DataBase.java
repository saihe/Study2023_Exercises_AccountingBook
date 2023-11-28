package jp.co.lbn.study.exercises.ksaito.infractructure;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.Objects;
import java.util.function.Consumer;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import org.hibernate.Session;

abstract class DataBase {
  private final EntityManagerFactory emf;

  DataBase() {
    emf = Persistence.createEntityManagerFactory("AccountingBook", System.getProperties());
  }

  protected Session getSession() {
    return emf.createEntityManager().unwrap(Session.class);
  }

  protected CriteriaBuilder criteriaBuilder() {
    return getSession().getCriteriaBuilder();
  }

  protected <T> TypedQuery<T> query(CriteriaQuery<T> specifiedQuery) {
    return getSession().createQuery(specifiedQuery);
  }

  protected void transaction(Consumer<Session> transactionFunction) throws AppException {
    transaction(null, transactionFunction);
  }

  protected void transaction(
      Session session,
      Consumer<Session> transactionFunction
  ) throws AppException {
    if (Objects.nonNull(session)) {
      transactionFunction.accept(session);
      return;
    }
    session = getSession();
    var tx = Objects.requireNonNull(session.beginTransaction());
    try {
      transactionFunction.accept(session);
      tx.commit();
    } catch (Exception ex) {
      try {
        tx.rollback();
      } catch (Exception ex2) {
        throw new AppException(ex2);
      }
      throw new AppException(ex);
    }
  }
}
