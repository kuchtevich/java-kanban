package service;
import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private HistoryManager historyManager;
    TaskManager taskManager;

     @Before
     public void beforeEach(){
         InMemoryTaskManager historyManager = new HistoryManager();
         taskManager = new InMemoryTaskManagerTest(historyManager);
     }

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void shouldBeTheTaskIsEquals() {
        Task task = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW,1);
        Task task2 = new Task("Cоздание новой задачи",
                "сменить город", Status.IN_PROGRESS,2);
        assertEquals(task,task2, "Задачи не совпадают.");


    }

    //проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void shouldBeTheTaskIsEqualsInClassExtends() {

    }

    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    @Test
    void shouldBeTheEpicIsNotEqualsEpic() {

    }
    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    @Test
    void ...(){

    }

    //убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void shouldBeTheManagerReturnInitializationAndReadyForWork() {

    }

    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void shouldBeTheManagerReturnInitializationAndReadyForWork() {

    }
    //*проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    void shouldBeTaskIdAndGenerateIdEquals() {

    }
    //создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void shouldBeTaskNotChange() {

    }
    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void shouldBeHistoryManagerSavePreviousVersion() {
        Task task = new Task("Cоздание новой задачи", "сменить профессию", Status.NEW);
        histotyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }




    //Пример
    @Test
    void addNewTask() {
        Task task = new Task("Cоздание новой задачи", "сменить профессию", Status.New, 1);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }
}