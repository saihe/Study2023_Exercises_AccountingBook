package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class HomeViewTest {
    private final View target = new HomeView();

    @Test
    void initialDisplay() {
        assertDoesNotThrow(() -> {
            var out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            target.draw();
            assertLinesMatch(
                List.of(
                    "\033[H\033[2Jお小遣い出納帳アプリ",
                    "\tメニュー",
                    "\t\t1\t収支入力",
                    "\t\t2\t収支検索",
                    "\t\tq\tアプリケーションを終了",
                    "Enterキーで入力を確定する",
                    "入力："
                ),
                List.of(out.toString().split(System.lineSeparator()))
            );
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void process(String selected) {
        var expect = switch(selected) {
            case "1" -> InputView.class;
            case "2" -> SearchView.class;
            default -> HomeView.class;
        };
        assertDoesNotThrow(() -> {
            target.process(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(selected.getBytes()))));
            assertEquals(expect, target.nextView.getClass());
        });
    }
}