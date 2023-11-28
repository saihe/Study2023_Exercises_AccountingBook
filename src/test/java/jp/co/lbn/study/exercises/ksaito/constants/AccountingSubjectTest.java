package jp.co.lbn.study.exercises.ksaito.constants;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountingSubjectTest {
    @Test
    void getById() {
        assertDoesNotThrow(() ->
            assertEquals(AccountingSubject.MISCELLANEOUS_EXPENSES, Constants.getById(AccountingSubject.class, UUID.fromString("cb5eaf06-b980-4b41-891e-f19d72c1b651")))
        );
    }

    @Test
    void getByCode() {
        assertDoesNotThrow(() ->
            assertEquals(AccountingSubject.FOOD_EXPENSES, Constants.getByCode(AccountingSubject.class, 2))
        );
    }
}