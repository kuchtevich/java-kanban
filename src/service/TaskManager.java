package service;

import model.*;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    List<Task> getAllTasks();

    List<SubTask> getAllSubTasks();

    List<Epic> getAllEpics();

    void removeAllTask();

    void removeAllSubtasks();

    void removeAllEpics();

    Task getTask(int id);

    SubTask getSubtask(int id);

    Epic getEpic(int id);

    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    SubTask addNewSubtask(SubTask subTask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(SubTask subTask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    List<SubTask> getAllEpicSubtasks(int epicId);
}
