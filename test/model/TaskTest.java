
package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    // проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void testEquals() {
        // arrange
        Task task1 = new Task("name 1", "description 1", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000));
        Task task2 = new Task("name 2", "description 2", Status.DONE,
                LocalDateTime.of(2024, 11, 11, 11, 11), Duration.ofMinutes(800000));

        // act + assert
        assertEquals(task1, task2);
    }

    @Test
    void testInheritorEquals() {
        // arrange
        // String name, String description, Status status, int id
        Task task1 = new SubTask("name 1", "description 1", Status.NEW, LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000), 1);
        Task task2 = new Epic("name 2", "description 2");

        // act + assert
        assertEquals(task1, task2);
    }
}
