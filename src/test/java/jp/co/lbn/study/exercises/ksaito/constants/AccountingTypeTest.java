package jp.co.lbn.study.exercises.ksaito.constants;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountingTypeTest {

    @Test
    void getById() {
        assertDoesNotThrow(() ->
            assertEquals(AccountingType.EXPENDITURE, Constants.getById(AccountingType.class, UUID.fromString("e3c79687-f082-4d3e-abfa-b922151b4080")))
        );
    }

    @Test
    void getByCode() {
        assertDoesNotThrow(() ->
            assertEquals(AccountingType.EXPENDITURE, Constants.getByCode(AccountingType.class, 2))
        );
    }
}