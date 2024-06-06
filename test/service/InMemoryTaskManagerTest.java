package service;

import model.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void shouldBeTheTaskIsEquals() {
        Task task = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW);
        Task task2 = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW);
        assertEquals(task, task2, "Задачи не совпадают.");
    }


    // убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void shouldBeHistoryManagerSavePreviousVersion() {
        // arrange // given
        int taskId = 1, expectedHistorySize = 1;
        Task task = new Task("Cоздание новой задачи", "сменить профессию", Status.NEW);
        taskManager.addNewTask(task);

        // act     // when
        taskManager.getTask(taskId);

        // assert  // then
        final List<Task> history = taskManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(expectedHistorySize, history.size(), "История не пустая.");
    }

    // проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void shouldAddTaskAndGetTaskById() {
        // arrange
        int taskId = 1;
        Task task = new Task("Cоздание новой задачи", "сменить профессию", Status.NEW);

        // act
        taskManager.addNewTask(task);

        // assert
        assertEquals(task, taskManager.getTask(taskId));
    }

    @Test
    void shouldAddSubTaskAndGetSubTaskById() {
        // arrange
        int taskId = 33, eoicId = 1;
        Task task = new SubTask("Cоздание новой задачи", "сменить профессию", Status.NEW, taskId);

        // act
        taskManager.addNewTask(task);

        // assert
        assertEquals(task, taskManager.getTask(taskId));
    }

    @Test
    void shouldAddEpicAndGetEpicById() {
        // arrange
        int epicId = 1;
        Task task = new Epic("Cоздание новой задачи", "сменить профессию");

        // act
        taskManager.addNewTask(task);

        // assert
        assertEquals(task, taskManager.getTask(epicId));
    }

}