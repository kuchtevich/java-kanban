package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    TaskManager taskManager;
    private File file;

    @BeforeEach
    public void beforeEach() throws IOException {
        file = File.createTempFile("file", ".csv");
    }

    @AfterEach
    public void afterEach() {
        if (file != null && file.exists()) {
            boolean delete = file.delete();
            System.out.println(delete + " Файл успешно удален");
        }
    }

    @Test
    public void shouldBeGetInformationInFile() throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        Task task = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW, LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000));
        Epic epic = new Epic("Создание нового эпика", "учеба");
        SubTask subTask = new SubTask("Создание новой подзадачи",
                "учеба", Status.NEW, LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000), 2);
        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpic(epic);
        fileBackedTaskManager.addNewSubtask(subTask);
        FileBackedTaskManager fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileBackedTaskManager.getAllTasks(), fileBackedTaskManager2.getAllTasks(), "ОШибка");
        assertEquals(fileBackedTaskManager.getAllEpics(), fileBackedTaskManager2.getAllEpics(), "Ошибка");
        assertEquals(fileBackedTaskManager.getAllSubTasks(), fileBackedTaskManager2.getAllSubTasks(), "Ошибка");
    }

    @Test
    public void shouldWriteTasksInFile() throws IOException {
        Task task = new Task("task1", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000));
        taskManager.addNewTask(task);

        Epic epic = new Epic("epic1", "description");
        taskManager.addNewEpic(epic);

        SubTask subTask = new SubTask("subtask1", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000), 2);
        taskManager.addNewSubtask(subTask);

        String fr = Files.readString(file.toPath());
        String[] lines = fr.split(";");

        assertEquals(4, lines.length, "Ошибка");
    }

    @Test
    public void shouldWriteAndDeleteTasksInFileTest() throws IOException {
        Task task = new Task("task1", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000));
        taskManager.addNewTask(task);
        Task task2 = new Task("task2", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600000));
        taskManager.addNewTask(task2);
        taskManager.deleteTask(2);

        Epic epic = new Epic("epic1", "description");
        taskManager.addNewEpic(epic);
        taskManager.removeAllEpics();

        FileBackedTaskManager f2 = FileBackedTaskManager.loadFromFile(file);

        assertEquals(1, taskManager.getAllTasks(), "Удаление неверно");
        assertEquals(0, taskManager.getAllEpics(), "Удаление неверно");
    }

    @Test
    public void shouldCreateTaskAndSaveFromFile() {
        Task task = new Task("task1", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(6000));
        taskManager.addNewTask(task);

        assertNotNull(taskManager.getHistory(), "Пусто");
        assertNotNull(taskManager.getAllTasks(), "Пусто");
    }

    @Test
    public void shouldTimeTest() {
        Task task = new Task("task1", "description", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(600));
        taskManager.addNewTask(task);

        Epic epic = new Epic("Новый эпик1", "Описание");
        taskManager.addNewEpic(epic);

        SubTask subTask = new SubTask("Новая подзадача1", "Описание", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(6000), 3);
        taskManager.addNewSubtask(subTask);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm");

        Epic epicTest = taskManager.getEpic(2);
        String startTimeEpic = epicTest.getStartTime().format(formatter);
        String endTimeEpic = epicTest.getEndTime().format(formatter);

        Duration durationEpic = epicTest.getDuration();
        LocalDateTime start = subTask.getStartTime();
        LocalDateTime finish = subTask.getEndTime();
        Duration duration = Duration.between(start, finish);

        assertEquals("16.06.2024_14:44", startTimeEpic, "Ошибка");
        assertEquals("16.06.2024_14:45", endTimeEpic, "Ошибка");
        assertEquals(duration, durationEpic, "Ошибка");
    }

}
