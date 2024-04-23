package service;
import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    //убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    //*проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    //создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.


    @Test
    void addNewTask() {
        Task task = new Task("Cоздание новой задачи", "сменить профессию", Status.New,1);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {
    }

    @Test
    void addNewSubtask() {
    }
}