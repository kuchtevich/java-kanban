package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest { //проверить удаление с начала, с конца и середины

    @Test
    void TestRemoveFirst() { //удаление из начала
        // подготовка
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task1 = new Task("task1", "task1", Status.NEW, 1);
        manager.add(task1);
        Task task2 = new Task("task2", "task2", Status.IN_PROGRESS, 2);
        manager.add(task2);
        Task task3 = new Task("task3", "task3", Status.NEW, 3);
        manager.add(task3);

        // действие
        manager.remove(task1.getId());
        assertEquals(manager.getAll(), List.of(task2, task3), "Проверка пройдена.");
    }

    @Test
    void TestRemoveLast() { //удаление из начала
        // подготовка
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task1 = new Task("task1", "task1", Status.NEW, 1);
        manager.add(task1);
        Task task2 = new Task("task2", "task2", Status.IN_PROGRESS, 2);
        manager.add(task2);
        Task task3 = new Task("task3", "task3", Status.NEW, 3);
        manager.add(task3);

        // действие
        manager.remove(task3.getId());
        assertEquals(manager.getAll(), List.of(task1, task2), "Проверка пройдена.");
    }

    @Test
    void TestRemoveMiddle() { //удаление из начала
        // подготовка
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task1 = new Task("task1", "task1", Status.NEW, 1);
        manager.add(task1);
        Task task2 = new Task("task2", "task2", Status.IN_PROGRESS, 2);
        manager.add(task2);
        Task task3 = new Task("task3", "task3", Status.NEW, 3);
        manager.add(task3);

        // действие
        manager.remove(task2.getId());
        assertEquals(manager.getAll(), List.of(task1, task3), "Проверка пройдена.");
    }
}

