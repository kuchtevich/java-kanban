package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private static File file;
    private static FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    public void beforeEach() throws IOException {
        file = File.createTempFile("file", "csv");
        fileBackedTaskManager = new FileBackedTaskManager();
    }


    @Test
    public void shouldWriteTasksInFile() throws IOException {
        Task task = new Task("Task1", "Descr1", Status.NEW);
        fileBackedTaskManager.addNewTask(task);

        Epic epic1 = new Epic("Epic1", "Description");
        fileBackedTaskManager.addNewEpic(epic1);

        SubTask subTask = new SubTask("SubTask1", "Description", Status.NEW, 2);
        fileBackedTaskManager.addNewSubtask(subTask);

        String from = Files.readString(file.toPath());
        String[] line = from.split(";");

        assertEquals(6, line.length, "Колчество строк неверное");
        assertEquals("id,type,name,description,status,epic", line[0], "Error");
        assertEquals("1,TASK,Новая задача1,NEW,Описание,null", line[1], "Error");
    }

}
