package jp.co.lbn.study.exercises.ksaito.infractructure;

import java.time.LocalDateTime;
import java.util.UUID;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingSubject;
import jp.co.lbn.study.exercises.ksaito.constants.AccountingType;
import jp.co.lbn.study.exercises.ksaito.domain.entity.Accounting;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import jp.co.lbn.study.exercises.ksaito.migration.Migration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountingRepositoryTest {
  private final AccountingRepository target = new AccountingRepository();

  @BeforeAll
  static void init() throws AppException {
    System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
    new Migration().migrate();
  }

  @BeforeEach
  void setUp() throws AppException {
    target.transaction(session -> session.createNativeMutationQuery(
        "insert into accounting (" +
            "id, years, months, dates, accountingTypeId, accountingSubjectId, amount, createdAt, updatedAt" +
            ") values ('00000000-0000-4000-0000-000000000001', '2023', '12', '31', '" +
            AccountingType.REVENUE.getId().toString() +
            "', '" +
            AccountingSubject.FOOD_EXPENSES.getId().toString() +
            "', 100000, '" +
            LocalDateTime.now() +
            "', '" +
            LocalDateTime.now() +
            "')"
    ).executeUpdate());
  }

  @AfterEach
  void tearDown() throws AppException {
    target.transaction(session -> session.createNativeMutationQuery(
        "delete from accounting"
    ).executeUpdate());
  }

  @Nested
  class Find {
    @ParameterizedTest
    @ValueSource(strings = {"00000000-0000-0000-0000-000000000000", "00000000-0000-4000-0000-000000000001"})
    void id(String id) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().id(UUID.fromString(id)).build());
        if (id.equals("00000000-0000-4000-0000-000000000001")) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(strings = {"9999", ""})
    void year(String year) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().years(year).build());
        if (year.isBlank()) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(strings = {"9999", ""})
    void month(String month) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().months(month).build());
        if (month.isBlank()) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(strings = {"9999", ""})
    void date(String date) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().dates(date).build());
        if (date.isBlank()) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(strings = {"00000000-0000-0000-0000-000000000000", "2d6b318d-141e-4cfd-ab66-fdef657299cb"})
    void accountingTypeId(String accountingTypeId) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().accountingTypeId(UUID.fromString(accountingTypeId)).build());
        if (accountingTypeId.equals("2d6b318d-141e-4cfd-ab66-fdef657299cb")) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(strings = {"00000000-0000-0000-0000-000000000000", "5ebb50b9-9a50-473b-9ab5-2f25ccd2b4f9"})
    void accountingSubjectId(String accountingSubjectId) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().accountingSubjectId(UUID.fromString(accountingSubjectId)).build());
        if (accountingSubjectId.equals("5ebb50b9-9a50-473b-9ab5-2f25ccd2b4f9")) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
    @ParameterizedTest
    @ValueSource(longs = {0, 100000})
    void amount(Long amount) {
      assertDoesNotThrow(() -> {
        var res = target.find(Accounting.builder().amount(amount).build());
        if (amount == 100000) {
          assertEquals(1, res.size());
        } else {
          assertEquals(0, res.size());
        }
      });
    }
  }

  @Test
  void create() {
    assertDoesNotThrow(() -> {
      var createdEntity = Accounting.builder()
          .years("2023")
          .months("12")
          .dates("16")
          .accountingTypeId(AccountingType.REVENUE.getId())
          .accountingSubjectId(AccountingSubject.MISCELLANEOUS_EXPENSES.getId())
          .amount(100000L)
          .build();
      target.create(createdEntity);
      var got = target.find(createdEntity);
      assertAll(() -> {
        assertEquals(1, got.size());
        assertEquals(createdEntity.getId(), got.get(0).getId());
      });
    });
  }

  @Test
  void modify() {
    assertDoesNotThrow(() -> {
      var createdEntity = Accounting.builder()
          .years("2023")
          .months("12")
          .dates("16")
          .accountingTypeId(AccountingType.REVENUE.getId())
          .accountingSubjectId(AccountingSubject.MISCELLANEOUS_EXPENSES.getId())
          .amount(100000L)
          .build();
      target.create(createdEntity);
      var modifiedEntity = createdEntity.cloneBuilder()
          .years("2024")
          .build();
      target.modify(modifiedEntity);
      var got = target.find(Accounting.builder().id(createdEntity.getId()).build());
      assertAll(() -> {
        assertEquals(1, got.size());
        assertEquals("2024", got.get(0).getYears());
        assertEquals(createdEntity.getMonths(), got.get(0).getMonths());
        assertEquals(createdEntity.getDates(), got.get(0).getDates());
        assertEquals(createdEntity.getDates(), got.get(0).getDates());
        assertEquals(createdEntity.getAccountingTypeId(), got.get(0).getAccountingTypeId());
        assertEquals(createdEntity.getAccountingSubjectId(), got.get(0).getAccountingSubjectId());
        assertEquals(createdEntity.getAmount(), got.get(0).getAmount());
        assertEquals(createdEntity.getCreatedAt(), got.get(0).getCreatedAt());
        assertTrue(createdEntity.getUpdatedAt().before(got.get(0).getUpdatedAt()));
      });
    });
  }
}