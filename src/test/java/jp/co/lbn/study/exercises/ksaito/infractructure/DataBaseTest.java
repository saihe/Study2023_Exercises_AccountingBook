package jp.co.lbn.study.exercises.ksaito.infractructure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("uncheck")
class DataBaseTest {
  private static class Stub extends DataBase {
    Stub() {
      super();
    }
  }

  private DataBase target;

  @BeforeAll
  static void init() {
    System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
  }

  @BeforeEach
  void setUp() {
    target = new Stub();
  }

  @Test
  void connect() {
    assertDoesNotThrow(() ->
      assertNotNull(target.getSession())
    );
  }

  @Test
  void transaction() {
    assertDoesNotThrow(() -> {
        var session = target.getSession();
        var tx = session.beginTransaction();
        target.transaction(session, local ->
            local.createNativeMutationQuery("create table test (col1 varchar(10))")
        .executeUpdate());
        var tables = session.createNativeQuery(
            "select table_name from information_schema.tables where table_schema = 'PUBLIC'",
            String.class
          ).getResultList();
        assertTrue(tables.stream()
            .anyMatch(tableName -> tableName.equalsIgnoreCase("test")));
        tx.rollback();
      }
    );
  }
}