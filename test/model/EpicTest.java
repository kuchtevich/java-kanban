package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import service.HistoryManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Эпик")
class EpicTest {
    @Test
    @DisplayName("Должен совпадать со своей копией")
    void shouldEqualsWithCopy() {
        Epic epic = new Epic("Создание нового эпика", "учеба");
        Epic epicExpected = new Epic("Создание нового эпика", "учеба");
        assertEquals(epicExpected, epic, "Должны быть идентичны");
        assertEqualsTask(epicExpected, epic, "Должны быть идентичны");
    }

    private static void assertEqualsTask(Task expected, Task actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message + ". 10.");
        assertEquals(expected.getName(), actual.getName(), message + ". имя.");
    }


}