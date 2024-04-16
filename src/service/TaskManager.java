package service;

import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, SubTask> subTasks;
    public HashMap<Integer, Epic> epics;
    private int counter = 0;

    private int generateId() {
        return ++counter;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }


    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void removeAllTask() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
        }
    }

    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }


    public Task getTask(int id) {
        return tasks.get(id);
    }

    public SubTask getSubtask(int id) {
        return subTasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Task addNewTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addNewEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask addNewSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTask(subTask);
            epic.calcStatus();
        }
        return subTask;
    }

    public void updateTask(Task task) {
        Task saved = tasks.get(task.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            tasks.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
        } else {
            saved.setName(epic.getName());
            saved.setDescription(epic.getDescription());
            epic.calcStatus();
        }
    }

    public void updateSubtask(SubTask subTask) {
        SubTask saved = subTasks.get(subTask.getId());
        if (saved == null) {
            System.out.println("Ошибка!");
            return;
        } else {
            if (subTask.getEpicId() != subTask.getId()) {
                System.out.println("Ошибка!");
                return;
            } else {
                saved.setName(subTask.getName());
                saved.setDescription(subTask.getDescription());
                saved.setId(subTask.getId());
                saved.setStatus(subTask.getStatus());
                Epic epic = epics.get(subTask.getId());
                epic.calcStatus();
            }
        }
    }

    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Ошибка.");
        }

    }

    public void deleteEpic(int id) {
        if (!epics.containsKey(id)) {
            return;
        }

        Epic epic = epics.get(id);

        for (SubTask subTask : epic.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }

        // epic.removeAllSubtasks();
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        if (!subTasks.containsKey(id)) {
            return;
        }

        SubTask subTask = subTasks.get(id);

        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);

        epic.removeSubTask(subTask);
        subTasks.remove(id);

    }
    public List<SubTask> getAllEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubTasks();
    }


}


