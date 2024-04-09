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
        this.tasks= new HashMap<>();
        this.epics= new HashMap<>();
        this.subTasks= new HashMap<>();
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

    public List<SubTask> getAllEpicSubtasks(int epicId) {
   Epic epic = epics.get(epicId);
   List<SubTask> subTasks = getAllSubTasks();
        return subTasks;
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
        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);
        return subTask;

    }

    public void updateTask(Task task) {
        Task saved = tasks.get(task.getId());
        saved.setName(task.getName());
        saved.setDescription(task.getDescription());
        saved.setStatus(task.getStatus());
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        saved.setId(epic.getId());
        List<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus().equals(Status.DONE)) {
               Status status = Status.DONE;
            } else if (subTask.getStatus().equals(Status.IN_PROGRESS)) {
                Status status = Status.IN_PROGRESS;
            } else if (subTask.getStatus().equals(Status.NEW)) {
                Status status = Status.NEW;
            }
        }
        }

    public void updateSubtask(SubTask subTask) {
            SubTask saved = subTasks.get(subTask.getId());
            saved.setName(subTask.getName());
            saved.setDescription(subTask.getDescription());
            saved.setId(subTask.getId());
            saved.setStatus(subTask.getStatus());
    }
    public void deleteTask(int id) {
     tasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public void deleteSubtask(int id) {
        subTasks.remove(id);
     }

    public void deleteAllTasks() {
    tasks.clear();
    }

    public void deleteAllSubtasks() {
        subTasks.clear();
        for(Epic epic : epics.values()) {
            List<SubTask> subTasks = epic.getSubTasks();
            for(SubTask subTask : subTasks) {
                subTasks.remove(subTask.getId());
            }
           epic.getSubTasks().clear();
        }
        }

    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            List<SubTask> subTasks = epic.getSubTasks();
            for (SubTask subTask : subTasks) {
                subTasks.remove(subTask.getId());
            }
            epic.getSubTasks().clear();
        }
    }

    }


