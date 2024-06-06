package service;

import java.io.File;

public class Manager {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager() {
        return new FileBackedTaskManager(new File("/Users/dvkuchtevich/java-kanban/file.csv"));
    }
}
