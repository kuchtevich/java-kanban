package service;

import java.io.File;

public class Manager {
    public static TaskManager getDefault() {
        return getDefaultFileBackedTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager() {
        return FileBackedTaskManager.loadFromFile(new File("file.csv"));
    }
}
