package jp.co.lbn.study.exercises.ksaito.migration;

import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MigrationTest {
  @BeforeAll
  static void init() {
    System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
  }

  @Test
  @SuppressWarnings("unchecked")
  void migrate() {
    assertDoesNotThrow(() -> {
      new Migration().migrate();
      var em = Persistence.createEntityManagerFactory("AccountingBook")
          .createEntityManager();
      var tables = em.createNativeQuery(
          "select table_name from information_schema.tables where table_schema = 'PUBLIC'",
            String.class
          )
          .getResultList();
      assertTrue(tables.stream().anyMatch(o -> o.toString().equalsIgnoreCase("processedMigration")));
    });
  }
}