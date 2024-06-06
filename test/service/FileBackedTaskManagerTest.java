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

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private File saveFile;

    @BeforeEach
    public void beforeEach() throws IOException {
        saveFile = File.createTempFile("file", ".csv", new File("/Users/dvkuchtevich/java-kanban/resourse"));
    }

    @AfterEach
    public void afterEach() {
        if (saveFile != null && saveFile.exists()) {
            boolean delete = saveFile.delete();
            System.out.println(delete + " Файл успешно удален");
        }
    }

// 6yt

    @Test
    public void shouldBeGetInformationInFile() throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(saveFile);
        Task task = new Task("Cоздание новой задачи",
                "сменить профессию", Status.NEW);
        Epic epic = new Epic("Создание нового эпика", "учеба");
        SubTask subTask = new SubTask("Создание новой подзадачи",
                "учеба", Status.IN_PROGRESS, 2);
        fileBackedTaskManager.addNewTask(task);
        fileBackedTaskManager.addNewEpic(epic);
        fileBackedTaskManager.addNewSubtask(subTask);
        FileBackedTaskManager fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(saveFile);
        assertEquals(fileBackedTaskManager.getAllTasks(), fileBackedTaskManager2.getAllTasks(), "Списки задач не совпадают");
        assertEquals(fileBackedTaskManager.getAllEpics(), fileBackedTaskManager2.getAllEpics(), "Списки эпиков не совпадают");
        assertEquals(fileBackedTaskManager.getAllSubTasks(), fileBackedTaskManager2.getAllSubTasks(), "Списки подзадач не совпадают");
    }
}
