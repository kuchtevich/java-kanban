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
    public void shouldBeGetInformationInFile() throws IOException {
        FileBackedTaskManager files = FileBackedTaskManager.loadFromFile(file);
        List<Task> list = files.getAllTasks();
        List<Epic> list1 = files.getAllEpics();
        List<SubTask> list2 = files.getAllSubTasks();

        assertEquals(0, list.size());
        assertEquals(0, list1.size());
        assertEquals(0, list2.size());
    }

}
