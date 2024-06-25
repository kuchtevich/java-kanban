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
import java.time.Duration;
import java.time.LocalDateTime;



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
    public void shouldBeGetInformationInFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        Task task = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW,
                LocalDateTime.of(2024, 12, 12, 12, 12), Duration.ofMinutes(1));
        Epic epic = new Epic("Создание нового эпика", "учеба");
        SubTask subTask = new SubTask("Создание новой подзадачи",
                "учеба", Status.NEW,
                LocalDateTime.of(2026, 12, 12, 12, 12), Duration.ofMinutes(1), 2);
        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpic(epic);
        fileBackedTaskManager.addNewSubtask(subTask);
        FileBackedTaskManager fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileBackedTaskManager.getAllTasks(), fileBackedTaskManager2.getAllTasks(), "ОШибка");
        assertEquals(fileBackedTaskManager.getAllEpics(), fileBackedTaskManager2.getAllEpics(), "Ошибка");
        assertEquals(fileBackedTaskManager.getAllSubTasks(), fileBackedTaskManager2.getAllSubTasks(), "Ошибка");
    }
}
