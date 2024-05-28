package service;
import model.*;

import java.util.List;

public interface TaskManager {
    Task getTask(int id);

    SubTask getSubtask(int id);

    Epic getEpic(int id);
    List<Task> getHistory();
    List<Task> getAllTasks();
    List<SubTask> getAllSubTasks();
    List<Epic> getAllEpics();

    List<SubTask> getAllEpicSubtasks(int epicId);

    Task addNewTask(Task task);
    Epic addNewEpic(Epic epic);
    SubTask addNewSubtask(SubTask subTask);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(SubTask subTask);

    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);

    void removeAllTask();

    void removeAllSubtasks();

    void removeAllEpics();
}
