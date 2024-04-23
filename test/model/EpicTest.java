package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Эпик")
class EpicTest {
    @Test
    @DisplayName("Должен совпадать со своей копией")
    void shouldEqualsWithCopy(){
        Epic epic = new Epic("Создание нового эпика", "учеба", 3);
        Epic epicExpected = new Epic ("Создание нового эпика1", "учеба", 3);
        assertEquals(epicExpected, epic, "Должны быть идентичны");
        assertEqualsTask(epicExpected, epic, "Должны быть идентичны");
    }

    @Test
    private static void assertEqualsTask(Task expected, Task actual, String message){
        assertEquals(expected.getId(), actual.getId(), message + ". 10.");
        assertEquals(expected.getName(), actual.getName(), message + ". имя.");
    }

}