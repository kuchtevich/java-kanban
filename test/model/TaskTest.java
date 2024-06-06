
package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    // проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void testEquals() {
        // arrange
        Task task1 = new Task("name 1", "description 1", Status.NEW);
        Task task2 = new Task("name 2", "description 2", Status.DONE);

        // act + assert
        assertEquals(task1, task2);
    }

    @Test
    void testInheritorEquals() {
        // arrange
        // String name, String description, Status status, int id
        Task task1 = new SubTask("name 1", "description 1", Status.NEW, 1);
        Task task2 = new Epic("name 2", "description 2");

        // act + assert
        assertEquals(task1, task2);
    }
}
